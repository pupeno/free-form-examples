;;;; Copyright © 2015-2017 José Pablo Fernández Silva

(ns free-form-examples.forms.re-frame.bootstrap-3-horizontal
  (:require [reagent.ratom :as ratom :include-macros true]
            [re-frame.core :as re-frame]
            [free-form.re-frame :as free-form]
            free-form.bootstrap-3
            [free-form-examples.layout :as layout]))

(re-frame/reg-sub-raw
  :re-frame-bootstrap-3-horizontal
  (fn [db]
    (ratom/reaction (:re-frame-bootstrap-3-horizontal @db))))

(re-frame/reg-event-db
  :update-re-frame-bootstrap-3-horizontal
  (fn [db [_ keys value :as event]]
    (-> db
        (assoc-in (cons :re-frame-bootstrap-3-horizontal keys) value)
        (update :event-log #(conj % event)))))

(defn- view []
  (let [data (re-frame/subscribe [:re-frame-bootstrap-3-horizontal])]
    (fn []
      [:div.bootstrap-3
       [layout/source-code-button "re_frame/bootstrap_3_horizontal.cljs"]
       [:h1 "Re-frame Bootstrap 3 Horizontal"]
       [free-form/form @data (:-errors @data) :update-re-frame-bootstrap-3-horizontal :bootstrap-3
        [:form.form-horizontal {:noValidate true}
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
         [:free-form/field {:type    :select
                            :label   "Select"
                            :key     :select
                            :options ["" ""
                                      :dog "Dog"
                                      :cat "Cat"
                                      :squirrel "Squirrel"
                                      :giraffe "Giraffe"]}]
         [:free-form/field {:type    :select
                            :label   "Select with group"
                            :key     :select-with-group
                            :options ["" ""
                                      "Numbers" [:one "One"
                                                 :two "Two"
                                                 :three "Three"
                                                 :four "Four"]
                                      "Letters" [:a "A"
                                                 :b "B"
                                                 :c "C"
                                                 :d "D"]]}]
         [:free-form/field {:type  :textarea
                            :label "Text area"
                            :key   :textarea}]
         [:free-form/field {:type  :text
                            :label "Text with deep keys"
                            :keys  [:t :e :x :t]}]
         [:free-form/field {:type                        :text
                            :key                         :text-with-extra-validation-errors
                            :extra-validation-error-keys [[:text] [:-general]]
                            :label                       "Text with extra validation errors"
                            :placeholder                 "This will be marked as a validation error also when Text and General have validation errors."}]
         [:free-form/field {:type  :checkbox
                            :key   :checkbox
                            :label "Checkbox"}]
         [:free-form/field {:type    :radio
                            :label   "Radio buttons"
                            :key     :radio-buttons
                            :options [:dog "Dog"
                                      :cat "Cat"
                                      :squirrel "Squirrel"
                                      :giraffe "Giraffe"]}]
         [:div.form-group
          [:div.col-sm-offset-2.col-sm-5
           [:button.btn.btn-primary {:type :submit}
            "Button"]]]]]
       [:hr]
       [:h2 "Controls"]
       [layout/controls :re-frame {:target :re-frame-bootstrap-3-horizontal}]
       [layout/state @data :re-frame-bootstrap-3-horizontal]
       [layout/event-log]])))

(defmethod layout/pages :re-frame-bootstrap-3-horizontal [_]
  [view])
