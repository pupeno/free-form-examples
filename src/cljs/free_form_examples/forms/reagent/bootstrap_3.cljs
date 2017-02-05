;;;; Copyright © 2015-2017 José Pablo Fernández Silva

(ns free-form-examples.forms.reagent.bootstrap-3
  (:require [reagent.core :as reagent]
            [free-form.core :as free-form]
            [free-form-examples.layout :as layout]))

(defn- view []
  (let [data (reagent/atom {})]
    (fn []
      [:div
       [layout/source-code-button "reagent/bootstrap_3.cljs"]
       [:h1 "Reagent Bootstrap 3"]
       [free-form/form @data (:-errors @data) (fn [keys value] (swap! data #(assoc-in % keys value))) :bootstrap-3
        [:form {:noValidate true}
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
         [:button.btn.btn-primary {:type :submit} "Button"]]]
       [:h2 "Controls"]
       [layout/controls :reagent {:form-data data}]
       [layout/state @data]])))

(defmethod layout/pages :reagent-bootstrap-3 [_]
  [view])
