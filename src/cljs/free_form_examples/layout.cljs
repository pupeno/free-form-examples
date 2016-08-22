;;;; Copyright © 2015, 2016 José Pablo Fernández Silva, All rights reserved.

(ns free-form-examples.layout
  (:require [cljs.pprint :as pp]
            [reagent.ratom :as ratom :include-macros true]
            [re-frame.core :as re-frame]
            [free-form.core :as free-form]
            [free-form.re-frame :as re-frame-free-form]
            [free-form-examples.routing :as routing]))

(defmulti pages :name)

(defmethod pages nil [_]                                    ; While the app is loading, the current-route is nil for an instant. Without this we would be showing :default, which is a page not found error.
  [:div [:h1 "Loading"]])

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
            [:li [:a {:href (routing/url-for :reagent-plain)} "Plain"]]
            [:li [:a {:href (routing/url-for :reagent-bootstrap-3)} "Bootstrap 3"]]
            [:li [:a {:href (routing/url-for :reagent-bootstrap-3-horizontal)} "Bootstrap 3 Horizontal"]]
            [:li [:a {:href (routing/url-for :reagent-bootstrap-3-inline)} "Bootstrap 3 Inline"]]]]
          [:li.dropdown
           [:a.dropdown-toggle {:href "#" :data-toggle "dropdown" :role "button" :aria-haspopup true :aria-expanded false}
            "Re-frame" [:span.caret]]
           [:ul.dropdown-menu
            [:li [:a {:href (routing/url-for :re-frame-plain)} "Plain"]]
            [:li [:a {:href (routing/url-for :re-frame-bootstrap-3)} "Bootstrap 3"]]
            [:li [:a {:href (routing/url-for :re-frame-bootstrap-3-horizontal)} "Bootstrap 3 Horizontal"]]
            [:li [:a {:href (routing/url-for :re-frame-bootstrap-3-inline)} "Bootstrap 3 Inline"]]
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
          "Copyright © 2015, 2016 "
          [:a {:href "https://pupeno.com"} "J. Pablo Fernández"]
          ", All rights reserved."]]]])))

(defmethod pages :home [_]
  (fn [_]
    [:div
     [:h1 "Free-form example application"]
     [:p "This is an example application for the "
      [:a {:href "https://github.com/pupeno/free-form"} "Free-form"]
      " library. A library to build forms in ClojureScript that's super flexible giving you the freedom to shape
      the form anyway you want at the same time as helping you play nice with both Reagent and Re-Frame."]]))

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

(defn- add-validation-error [event data validation-error]
  (.preventDefault event)
  (swap! data (fn [data]
                (let [error-field (keyword (:field @validation-error))
                      error-message (:message @validation-error)]
                  (update-in data [:-errors error-field] #(cons error-message %1))))))

(re-frame/register-handler
  :update-validation-error
  (fn [db [_ keys value]]
    (-> db
        (assoc-in (cons :validation-error keys) value))))

(re-frame/register-sub
  :validation-error
  (fn [db]
    (ratom/reaction (:validation-error @db))))

(re-frame/register-handler
  :add-validation-errors-to-form
  (fn [db [_ target]]
    (let [error-field (keyword (:field (:validation-error db)))
          error-message (:message (:validation-error db))]
      (.log js/console (pr-str target) (pr-str error-field) (pr-str error-message))
      (update-in db [target :-errors error-field] #(cons error-message %1)))))

(defn ui-dispatch
  "Re-frame dispatch enhanced for UI. On top of re-frame-dispatching args, it:
  * stops the default handling from happening, so links and forms don't get submitted.
  * passes the target of the event (the form or link) as the last argument to the re-frame-dispatch."
  [js-event event]
  (.preventDefault js-event)
  (re-frame/dispatch event))

(defn validation-errors-control [mode data validation-error-or-reframe-target]
  [:div
   [:p "Add a validation error:"]
   (let [actual-form [:form.form-horizontal {:noValidate        true
                                             :free-form/options {:mode :bootstrap-3}
                                             :on-submit         (case mode
                                                                  :reagent #(add-validation-error %1 data validation-error-or-reframe-target)
                                                                  :re-frame #(ui-dispatch % [:add-validation-errors-to-form validation-error-or-reframe-target]))}
                      [:free-form/field {:type    :select
                                         :label   "Field"
                                         :key     :field
                                         :options ["" ""
                                                   "-general" "General"
                                                   "email" "Email"
                                                   "text" "Text"
                                                   "password" "Password"
                                                   "select" "Select"
                                                   "select-with-group" "Select with group"
                                                   "textarea" "Text area"
                                                   "text-with-extra-validation-errors" "Text with extra validation errors"]}]
                      [:free-form/field {:type        :text
                                         :key         :message
                                         :label       "Message"
                                         :placeholder "e.g.: Email should contain one and only one @."}]
                      [:div.form-group
                       [:div.col-sm-offset-2.col-sm-5
                        [:button.btn.btn-primary {:type :submit}
                         "Add Validation Error"]]]]]
     (case mode
       :reagent [free-form/form @validation-error-or-reframe-target {} (fn [keys value] (swap! validation-error-or-reframe-target #(assoc-in % keys value)))
                 actual-form]
       :re-frame (let [validation-error-data (re-frame/subscribe [:validation-error])]
                   [re-frame-free-form/form @validation-error-data {} :update-validation-error
                   actual-form])))])