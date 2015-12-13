;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns free-form-examples.forms.reagent.bootstrap-horizontal
  (:require [free-form.core :as free-form]
            [free-form-examples.layout :as layout]))

(defmethod layout/pages :reagent-bootstrap-horizontal [_]
  (fn []
    [:div
     [:h1 "Reagent Bootstrap Horizontal"]
     [free-form/form {} {} (fn [& args] (println args))
      [:form.form-horizontal {:noValidate        true
                              :free-form/options {:mode        :bootstrap
                                                  #_:label-width #_2
                                                  #_:value-width #_10}}
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
       [:div.form-group
        [:div.col-sm-offset-2.col-sm-5
         [:button.btn.btn-primary {:type :submit}
          "Button"]]]]]]))
