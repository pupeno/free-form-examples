;;;; Copyright © 2015, 2016 José Pablo Fernández Silva, All rights reserved.

(ns free-form-examples.forms.reagent.bootstrap-3
  (:require [reagent.core :as reagent]
            [free-form.core :as free-form]
            [free-form-examples.layout :as layout]))

(defmethod layout/pages :reagent-bootstrap-3 [_]
  (let [data (reagent/atom {})]
    (fn [_]
      [:div
       [layout/source-code-button "reagent/bootstrap_3.cljs"]
       [:h1 "Reagent Bootstrap 3"]
       [free-form/form {} {} (fn [keys value] (swap! data #(assoc-in % keys value)))
        [:form {:noValidate        true
                :free-form/options {:mode :bootstrap-3}}
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
         [:button.btn.btn-primary {:type :submit} "Button"]]]
       [layout/state @data]])))
