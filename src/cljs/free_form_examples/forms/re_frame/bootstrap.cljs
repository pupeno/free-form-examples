;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns free-form-examples.forms.re-frame.bootstrap
  (:require [reagent.ratom :as ratom :include-macros true]
            [re-frame.core :as re-frame]
            [free-form.re-frame :as free-form]
            [free-form-examples.layout :as layout]))

(re-frame/register-sub
  :re-frame-bootstrap
  (fn [db]
    (ratom/reaction (:re-frame-bootstrap @db))))

(re-frame/register-handler
  :update-re-frame-bootstrap
  (fn [db [_ keys value :as event]]
    (-> db
        (assoc-in (cons :re-frame-bootstrap keys) value)
        (update :event-log #(conj % event)))))

(defmethod layout/pages :re-frame-bootstrap [_]
  (let [data (re-frame/subscribe [:re-frame-bootstrap])]
    (fn []
      [:div
       [:h1 "Re-frame Bootstrap"]
       [free-form/form {} {} :update-re-frame-bootstrap
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
         [:button.btn.btn-primary {:type :submit} "Button"]]]
       [layout/state @data]
       [layout/event-log]])))
