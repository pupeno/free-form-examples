;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns free-form-examples.forms.re-frame.bootstrap-inline
  (:require [reagent.ratom :as ratom :include-macros true]
            [re-frame.core :as re-frame]
            [free-form.re-frame :as free-form]
            [free-form-examples.layout :as layout]))

(re-frame/register-sub
  :re-frame-bootstrap-inline
  (fn [db]
    (ratom/reaction (:re-frame-bootstrap-inline @db))))

(re-frame/register-handler
  :update-re-frame-bootstrap-inline
  (fn [db [_ keys value :as event]]
    (-> db
        (assoc-in (cons :re-frame-bootstrap-inline keys) value)
        (update :event-log #(conj % event)))))

(defmethod layout/pages :re-frame-bootstrap-inline [_]
  (let [data (re-frame/subscribe [:re-frame-bootstrap-inline])]
    (fn []
      [:div
       [:h1 "Re-frame Bootstrap Inline"]
       [free-form/form {} {} :update-re-frame-bootstrap-inline
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
       [layout/state @data]
       [layout/event-log]])))
