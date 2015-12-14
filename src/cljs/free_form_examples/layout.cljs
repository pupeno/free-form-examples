;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns free-form-examples.layout
  (:require [re-frame.core :as re-frame]
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
            [:li [:a {:href (routing/url-for :reagent-bootstrap)} "Bootstrap"]]
            [:li [:a {:href (routing/url-for :reagent-bootstrap-horizontal)} "Bootstrap Horizontal"]]
            [:li [:a {:href (routing/url-for :reagent-bootstrap-inline)} "Bootstrap Inline"]]]]
          [:li.dropdown
           [:a.dropdown-toggle {:href "#" :data-toggle "dropdown" :role "button" :aria-haspopup true :aria-expanded false}
            "Re-frame" [:span.caret]]
           [:ul.dropdown-menu
            [:li [:a {:href (routing/url-for :re-frame-plain)} "Plain"]]
            [:li [:a {:href (routing/url-for :re-frame-bootstrap)} "Bootstrap"]]
            [:li [:a {:href (routing/url-for :re-frame-bootstrap-horizontal)} "Bootstrap Horizontal"]]
            [:li [:a {:href (routing/url-for :re-frame-bootstrap-inline)} "Bootstrap Inline"]]]]
          [:li.dropdown
           [:a.dropdown-toggle {:href "#" :data-toggle "dropdown" :role "button" :aria-haspopup true :aria-expanded false}
            "Bootstrap" [:span.caret]]
           [:ul.dropdown-menu
            [:li [:a {:href (routing/url-for :reagent-bootstrap)} "On Reagent"]]
            [:li [:a {:href (routing/url-for :re-frame-bootstrap)} "On Reframe"]]]]]
         [:ul.nav.navbar-nav.navbar-right
          [:li
           [:a {:href "https://CarouselApps.com/free-form"} "Free Form " [:i.fa.fa-external-link]]]]]]])))

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
          "Copyright © 2015 "
          [:a {:href "https://carouselapps.com"} "Carousel Apps"]
          ", Ltd. All rights reserved."]]]])))

(defmethod pages :home [_]
  (fn [_]
    [:div
     [:h1 "Free-form example application"]
     [:p "This is an example application for the "
      [:a {:href "https://CarouselApps.com/free-form"} "Free-form"]
      " library. A library to build forms in ClojureScript that's super flexible giving you the freedom to shape
      the form anyway you want at the same time as helping you play nice with both Reagent and Re-Frame."]]))
