;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns free-form-examples.forms.re-frame.bootstrap-horizontal
  (:require [reagent.ratom :as ratom :include-macros true]
            [re-frame.core :as re-frame]
            [free-form.re-frame :as free-form]
            [free-form-examples.layout :as layout]))

(re-frame/register-sub
  :re-frame-bootstrap-horizontal
  (fn [db]
    (ratom/reaction (:re-frame-bootstrap-horizontal @db))))

(re-frame/register-handler
  :update-re-frame-bootstrap-horizontal
  (fn [db args]
    (println args)
    db))

(defmethod layout/pages :re-frame-bootstrap-horizontal [_]
  (let [data (re-frame/subscribe [:re-frame-bootstrap-horizontal])]
    (fn []
      [:div
       [:h1 "Re-frame Bootstrap Horizontal"]
       [free-form/form {} {} :update-re-frame-bootstrap-horizontal
        [:form.form-horizontal {:noValidate        true
                                :free-form/options {:mode :bootstrap
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
            "Button"]]]]]])))
