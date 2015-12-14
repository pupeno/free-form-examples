;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns free-form-examples.forms.reagent.bootstrap
  (:require [free-form.core :as free-form]
            [free-form-examples.layout :as layout]))

(defmethod layout/pages :reagent-bootstrap [_]
  (fn []
    [:div
     [layout/source-code-button "reagent/bootstrap.cljs"]
     [:h1 "Reagent Bootstrap"]
     [free-form/form {} {} (fn [& args] (println args))
      [:form {:noValidate        true
              :free-form/options {:mode :bootstrap}}
       [:div.col-sm-offset-2.col-sm-10 {:free-form/error-message {:key :-general}} [:p.text-danger]]
       [:free-form/field {:type        :text
                          :key         :text
                          :label       "Text"
                          :placeholder "placeholder"}]
       [:free-form/field {:type        :email
                          :key         :email
                          :label       "Email"
                          :placeholder "placeholder@example.com"}]
       [:free-form/field {:type  :password
                          :label "Password"
                          :keys  [:password]}]
       [:button.btn.btn-primary {:type :submit} "Button"]]]]))
