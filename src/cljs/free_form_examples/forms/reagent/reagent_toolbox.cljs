;;;; Copyright © 2017 José Pablo Fernández Silva

(ns free-form-examples.forms.reagent.reagent-toolbox
  (:require [reagent.core :as reagent]
            [free-form.core :as free-form]
            free-form.reagent-toolbox
            [free-form-examples.layout :as layout]
            [reagent-toolbox.core :as rt]))

(defn- view []
  (let [data (reagent/atom {})]
    (fn []
      [:div
       [layout/source-code-button "reagent/reagent-toolbox.cljs"]
       [:h1 "Reagent Reagent-Toolbox"]
       [free-form/form @data (:-errors @data) (fn [keys value] (swap! data #(assoc-in % keys value))) :reagent-toolbox
        [:form {:noValidate true}
         [:div {:free-form/error-message {:key :-general}} [:p.text-danger]]
         [:free-form/field {:type  :text
                            :key   :text
                            :label "Text"}]
         [:free-form/field {:type  :email
                            :key   :email
                            :label "Email"}]
         [:free-form/field {:type  :tel
                            :key   :tel
                            :label "Tel"}]
         [:free-form/field {:type  :password
                            :label "Password"
                            :keys  [:password]}]
         [:free-form/field {:type    :select
                            :key     :select
                            :label   "Select"
                            :options [:dog "Dog"
                                      :cat "Cat"
                                      :squirrel "Squirrel"
                                      :giraffe "Giraffe"]}]
         [:free-form/field {:type    :select
                            :label   "Select with group"
                            :key     :select-with-group
                            :options ["Numbers" [:one "One"
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
         [:free-form/field {:type  :switch
                            :key   :switch
                            :label "Switch"}]
         [:free-form/field {:type    :radio
                            :label   "Radio buttons"
                            :key     :radio-buttons
                            :options [:dog "Dog"
                                      :cat "Cat"
                                      :squirrel "Squirrel"
                                      :giraffe "Giraffe"]}]
         [rt/button {:label   "Button"
                     :raised  true
                     :primary true}]]]
       [:hr]
       [:h2 "Controls"]
       [layout/controls :reagent {:form-data data}]
       [layout/state @data]])))

(defmethod layout/pages :reagent-reagent-toolbox [_]
  [view])
