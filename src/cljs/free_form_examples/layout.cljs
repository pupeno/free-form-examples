;;;; Copyright © 2015-2017 José Pablo Fernández Silva

(ns free-form-examples.layout
  (:require [cljs.pprint :as pp]
            [reagent.core :as reagent]
            [reagent.ratom :as ratom :include-macros true]
            [re-frame.core :as re-frame]
            [free-form.core :as free-form]
            [free-form.re-frame :as re-frame-free-form]
            [free-form-examples.routing :as routing]))

(defn ui-dispatch
  "Re-frame dispatch enhanced for UI. On top of re-frame-dispatching args, it:
  * stops the default handling from happening, so links and forms don't get submitted.
  * passes the target of the event (the form or link) as the last argument to the re-frame-dispatch."
  [js-event event]
  (.preventDefault js-event)
  (re-frame/dispatch event))

(defmulti pages :name)

(defmethod pages nil [_]                                    ; While the app is loading, the current-route is nil for an instant. Without this we would be showing :default, which is a page not found error.
  [:div [:h1 "Loading..."]])

(defmethod pages :default [_]
  [:div [:h1 "Page not found"]])

(defn nav-bar []
  (let [current-route (re-frame/subscribe [:current-route])]
    (fn []
      [:nav.navbar.navbar-inverse.navbar-fixed-top
       [:div.container
        [:div.navbar-header
         [:button.navbar-toggle.collapsed {:type "button", :data-toggle "collapse", :data-target "#navbar", :aria-expanded "false", :aria-controls "navbar"}
          [:span.sr-only "Toggle navigation"]
          [:span.icon-bar]
          [:span.icon-bar]
          [:span.icon-bar]]
         [:a.navbar-brand {:href (routing/url-for :home)} "Free Form"]]
        [:div#navbar.collapse.navbar-collapse
         [:ul.nav.navbar-nav
          [:li.dropdown
           [:a.dropdown-toggle {:href "#" :data-toggle "dropdown" :role "button" :aria-haspopup true :aria-expanded false}
            "Reagent" [:span.caret]]
           [:ul.dropdown-menu
            [:li [:a {:href (routing/url-for :reagent-bootstrap-3)} "Bootstrap 3"]]
            [:li [:a {:href (routing/url-for :reagent-bootstrap-3-horizontal)} "Bootstrap 3 Horizontal"]]
            [:li [:a {:href (routing/url-for :reagent-bootstrap-3-inline)} "Bootstrap 3 Inline"]]
            [:li [:a {:href (routing/url-for :reagent-plain)} "Plain"]]]]
          [:li.dropdown
           [:a.dropdown-toggle {:href "#" :data-toggle "dropdown" :role "button" :aria-haspopup true :aria-expanded false}
            "Re-frame" [:span.caret]]
           [:ul.dropdown-menu
            [:li [:a {:href (routing/url-for :re-frame-bootstrap-3)} "Bootstrap 3"]]
            [:li [:a {:href (routing/url-for :re-frame-bootstrap-3-horizontal)} "Bootstrap 3 Horizontal"]]
            [:li [:a {:href (routing/url-for :re-frame-bootstrap-3-inline)} "Bootstrap 3 Inline"]]
            [:li [:a {:href (routing/url-for :re-frame-plain)} "Plain"]]
            [:li [:a {:href (routing/url-for :re-frame-state)} "State"]]]]
          [:li.dropdown
           [:a.dropdown-toggle {:href "#" :data-toggle "dropdown" :role "button" :aria-haspopup true :aria-expanded false}
            "Bootstrap 3" [:span.caret]]
           [:ul.dropdown-menu
            [:li [:a {:href (routing/url-for :reagent-bootstrap-3)} "On Reagent"]]
            [:li [:a {:href (routing/url-for :re-frame-bootstrap-3)} "On re-frame"]]]]]
         [:ul.nav.navbar-nav.navbar-right
          [:li
           [:a {:href "https://github.com/pupeno/free-form"} "Free Form " [:i.fa.fa-external-link]]]]]]])))

(defn main-panel []
  (let [current-route (re-frame/subscribe [:current-route])]
    (fn []
      [:div
       [nav-bar]
       [:main.container
        ^{:key @current-route} [pages @current-route]]
       [:footer.footer
        [:div.container
         [:p.text-muted
          "Copyright © 2015-2017 "
          [:a {:href "https://pupeno.com"} "José Pablo Fernández Silva"]]]]])))

(defmethod pages :home [_]
  (let [readme (re-frame/subscribe [:readme])]
    (fn [_]
      [:div
       [:h1 "Free-form example application"]
       [:p "This is an example application for the "
        [:a {:href "https://github.com/pupeno/free-form"} "Free-form"]
        " library. A library to build forms in ClojureScript that's super flexible giving you the freedom to shape
        the form anyway you want at the same time as helping you play nice with both Reagent and Re-Frame."]
       [:div.markdown-body {:dangerouslySetInnerHTML {:__html @readme}}]])))

(defmethod routing/display-page :home [_current-route db]
  (re-frame/dispatch [:request-readme])
  db)

(defmethod pages :re-frame-state [_]
  (let [db (re-frame/subscribe [:db])]
    (fn [_]
      [:div
       [:h1 "All of Re-frame state"]
       [:p
        "This is all the current Re-frame state, so you can inspect it. It can help understand the impact of
        Free-form."]
       [:pre (with-out-str (pp/pprint @db))]])))

(defn state [data key]
  (fn [data key]
    [:div
     [:h2 "State"]
     (when-not (nil? key)
       [:p
        "This form keeps the state in Re-frame's global state, in the " [:code (name key)]
        " key. Just play with the form and see it update: "])
     [:pre (with-out-str (pp/pprint data))]]))

(defn event-log []
  (let [event-log (re-frame/subscribe [:event-log])]
    (fn []
      [:div
       [:h2 "Events"]
       [:p "Free-form, when in Re-frame mode, fires events. These are the events:"]
       [:pre (map #(with-out-str (pp/pprint %)) @event-log)]])))

(defn source-code-button [file]
  [:div.pull-right
   [:a.btn.btn-default
    {:href   (str "https://github.com/pupeno/free-form-examples/blob/master/src/cljs/free_form_examples/forms/" file)
     :target "_blank"}
    [:span.fa.fa-github]
    " View Source Code"]])

(defn- add-validation-error [event form-data validation-error]
  (.preventDefault event)
  (swap! form-data (fn [form-data]
                     (let [error-field (keyword (:field @validation-error))
                           error-message (:message @validation-error)]
                       (update-in form-data [:-errors error-field] #(cons error-message %1))))))

(re-frame/reg-event-db
  :update-validation-error
  (fn [db [_ keys value]]
    (-> db
        (assoc-in (cons :validation-error keys) value))))

(re-frame/reg-sub-raw
  :validation-error
  (fn [db]
    (ratom/reaction (:validation-error @db))))

(re-frame/reg-event-db
  :add-validation-errors-to-form
  (fn [db [_ target]]
    (let [error-field (keyword (:field (:validation-error db)))
          error-message (:message (:validation-error db))]
      (update-in db [target :-errors error-field] #(cons error-message %1)))))

(defn- change-content [event form-data content-data]
  (.preventDefault event)
  (swap! form-data (fn [form-data]
                     (let [field (keyword (:field @content-data))
                           new-content (:new-content @content-data)]
                       (assoc form-data field new-content)))))

(re-frame/reg-event-db
  :update-change-content
  (fn [db [_ keys value]]
    (-> db
        (assoc-in (cons :change-content keys) value))))

(re-frame/reg-sub-raw
  :change-content
  (fn [db]
    (ratom/reaction (:change-content @db))))

(re-frame/reg-event-db
  :change-content-in-form
  (fn [db [_ target]]
    (let [field (keyword (:field (:change-content db)))
          new-content (:new-content (:change-content db))]
      (assoc-in db [target field] new-content))))

(defn controls [mode _]
  (let [validation-error-data (case mode
                                :reagent (reagent/atom {})
                                :re-frame (re-frame/subscribe [:validation-error]))
        change-content-data (case mode
                              :reagent (reagent/atom {})
                              :re-frame (re-frame/subscribe [:change-content]))]
    (fn [mode {:keys [form-data target]}]
      [:div
       [:h4 "Add a validation error"]
       (let [validation-error-form [:form.form-horizontal {:noValidate true
                                                           :on-submit  (case mode
                                                                         :reagent #(add-validation-error %1 form-data validation-error-data)
                                                                         :re-frame #(ui-dispatch % [:add-validation-errors-to-form target]))}
                                    [:free-form/field {:type    :select
                                                       :label   "Field"
                                                       :key     :field
                                                       :options ["" ""
                                                                 "-general" "General"
                                                                 "text" "Text"
                                                                 "email" "Email"
                                                                 "password" "Password"
                                                                 "select" "Select"
                                                                 "select-with-group" "Select with group"
                                                                 "textarea" "Text area"
                                                                 "text-with-extra-validation-errors" "Text with extra validation errors"
                                                                 "checkbox" "Checkbox"
                                                                 "radio-buttons" "Radio buttons"]}]
                                    [:free-form/field {:type        :text
                                                       :key         :message
                                                       :label       "Message"
                                                       :placeholder "e.g.: Email should contain one and only one @."}]
                                    [:div.form-group
                                     [:div.col-sm-offset-2.col-sm-5
                                      [:button.btn.btn-primary {:type :submit}
                                       "Add Validation Error"]]]]]
         (case mode
           :reagent [free-form/form @validation-error-data {} (fn [keys value] (swap! validation-error-data #(assoc-in % keys value))) :bootstrap-3
                     validation-error-form]
           :re-frame [re-frame-free-form/form @validation-error-data {} :update-validation-error :bootstrap-3
                      validation-error-form]))
       [:h4 "Change data"]
       (let [change-content-form [:form.form-horizontal {:noValidate true
                                                         :on-submit  (case mode
                                                                       :reagent #(change-content %1 form-data change-content-data)
                                                                       :re-frame #(ui-dispatch % [:change-content-in-form target]))}
                                  [:free-form/field {:type    :select
                                                     :label   "Field"
                                                     :key     :field
                                                     :options ["" ""
                                                               "text" "Text"
                                                               "email" "Email"
                                                               "password" "Password"
                                                               "select" "Select"
                                                               "select-with-group" "Select with group"
                                                               "textarea" "Text area"
                                                               "text-with-extra-validation-errors" "Text with extra validation errors"]}]
                                  [:free-form/field {:type  :text
                                                     :key   :new-content
                                                     :label "New content"}]
                                  [:div.form-group
                                   [:div.col-sm-offset-2.col-sm-5
                                    [:button.btn.btn-primary {:type :submit}
                                     "Change content"]]]]]
         (case mode
           :reagent [free-form/form @change-content-data {} (fn [keys value] (swap! change-content-data #(assoc-in % keys value))) :bootstrap-3
                     change-content-form]
           :re-frame [re-frame-free-form/form @change-content-data {} :update-change-content :bootstrap-3
                      change-content-form]))])))
