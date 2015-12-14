;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns free-form-examples.forms.reagent.bootstrap-inline
  (:require [reagent.core :as reagent]
            [free-form.core :as free-form]
            [free-form-examples.layout :as layout]))

(defmethod layout/pages :reagent-bootstrap-inline [_]
  (let [data (reagent/atom {})]
    (fn [_]
      [:div
       [layout/source-code-button "reagent/bootstrap_inline.cljs"]
       [:h1 "Reagent Bootstrap Inline"]
       [free-form/form {} {} (fn [keys value] (swap! data #(assoc-in % keys value)))
        [:form.form-inline {:noValidate        true
                            :free-form/options {:mode :bootstrap}}
         [:free-form/field {:type        :text
                            :key         :text
                            :label       "Text"
                            :placeholder "placeholder"}] " "
         [:free-form/field {:type        :email
                            :key         :email
                            :label       "Email"
                            :placeholder "placeholder@example.com"}] " "
         [:free-form/field {:type  :password
                            :label "Password"
                            :keys  [:password]}] " "
         [:button.btn.btn-primary {:type :submit} "Button"]]]
       [layout/state @data]])))
