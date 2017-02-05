;;;; Copyright © 2015-2017 José Pablo Fernández Silva

(ns free-form-examples.forms.reagent.plain
  (:require [reagent.core :as reagent]
            [free-form.core :as free-form]
            [free-form-examples.layout :as layout]))

(defn- view []
  (let [data (reagent/atom {})]
    (fn [_]
      [:div.plain-form
       [layout/source-code-button "reagent/plain.cljs"]
       [:h1 "Reagent"]
       [free-form/form @data (:-errors @data) (fn [keys value] (swap! data #(assoc-in % keys value)))
        [:form {:noValidate true}
         [:div.errors {:free-form/error-message {:key :-general}} [:p.error]]
         [:div.plain-field {:free-form/error-class {:key :text :error "validation-errors"}}
          [:label {:for :text} "Text"]
          [:input {:free-form/input {:key :text}
                   :type            :text
                   :id              :text
                   :placeholder     "placeholder"}]
          [:div.errors {:free-form/error-message {:key :text}} [:p.error]]]
         [:div.plain-field {:free-form/error-class {:key :email :error "validation-errors"}}
          [:label {:for :email} "Email"]
          [:input {:free-form/input {:key :email}
                   :type            :email
                   :id              :email
                   :placeholder     "placeholder@example.com"}]
          [:div.errors {:free-form/error-message {:key :email}} [:p.error]]]
         [:div.plain-field {:free-form/error-class {:key :password :error "validation-errors"}}
          [:label {:for :password} "Password"]
          [:input {:free-form/input {:key :password}
                   :type            :password
                   :id              :password}]
          [:div.errors {:free-form/error-message {:key :password}} [:p.error]]]
         [:div.plain-field {:free-form/error-class {:key :select :error "validation-errors"}}
          [:label {:for :select} "Select"]
          [:select {:free-form/input {:key :select}
                    :type            :select
                    :id              :select}
           [:option]
           [:option {:value :dog} "Dog"]
           [:option {:value :cat} "Cat"]
           [:option {:value :squirrel} "Squirrel"]
           [:option {:value :giraffe} "Giraffe"]]
          [:div.errors {:free-form/error-message {:key :select}} [:p.error]]]
         [:div.plain-field {:free-form/error-class {:key :select-with-group :error "validation-errors"}}
          [:label {:for :select} "Select with groups"]
          [:select {:free-form/input {:key :select-with-group}
                    :type            :select
                    :id              :select-with-group}
           [:option]
           [:optgroup {:label "Numbers"}
            [:option {:value :one} "One"]
            [:option {:value :two} "Two"]
            [:option {:value :three} "Three"]
            [:option {:value :four} "Four"]]
           [:optgroup {:label "Leters"}
            [:option {:value :a} "A"]
            [:option {:value :b} "B"]
            [:option {:value :c} "C"]
            [:option {:value :d} "D"]]]
          [:div.errors {:free-form/error-message {:key :select-with-group}} [:p.error]]]
         [:div.plain-field {:free-form/error-class {:key :textarea :error "validation-errors"}}
          [:label {:for :text-area} "Text area"]
          [:textarea {:free-form/input {:key :textarea}
                      :id              :textarea}]
          [:div.errors {:free-form/error-message {:key :textarea}} [:p.error]]]
         [:div.plain-field {:free-form/error-class {:key [:t :e :x :t] :error "validation-errors"}}
          [:label {:for :text} "Text with deep keys"]
          [:input {:free-form/input {:keys [:t :e :x :t]}
                   :type            :text
                   :id              :text
                   :placeholder     "placeholder"}]
          [:div.errors {:free-form/error-message {:keys [:t :e :x :t]}} [:p.error]]]
         [:div.plain-field {:free-form/error-class {:key        :text-with-extra-validation-errors :error "validation-errors"
                                                    :extra-keys [[:text] [:-general]]}}
          [:label {:for :text-with-extra-validation-errors} "Text with extra validation errors"]
          [:input {:free-form/input {:key :text-with-extra-validation-errors}
                   :type            :text
                   :id              :text-with-extra-validation-errors
                   :placeholder     "This will be marked as a validation error also when Text and General have validation errors."}]
          [:div.errors {:free-form/error-message {:key :text-with-extra-validation-errors}} [:p.error]]]
         [:div {:free-form/error-class {:key :checkbox :error "validation-errors"}}
          [:input {:free-form/input {:key :checkbox}
                   :type            :checkbox
                   :id              :checkbox}]
          [:label {:for :checkbox} "Checkbox"]
          [:div.errors {:free-form/error-message {:key :checkbox}} [:p.error]]]
         [:div.plain-field {:free-form/error-class {:key :radio-buttons :error "validation-errors"}}
          [:label
           [:input {:free-form/input {:key :radio-buttons}
                    :type            :radio
                    :name            :radio-buttons
                    :value           "radio-option-1"}]
           "Radio Option 1"]
          [:label
           [:input {:free-form/input {:key :radio-buttons}
                    :type            :radio
                    :name            :radio-buttons
                    :value           "radio-option-2"}]
           "Radio Option 2"]
          [:label
           [:input {:free-form/input {:key :radio-buttons}
                    :type            :radio
                    :name            :radio-buttons
                    :value           "radio-option-3"}]
           "Radio Option 3"]
          [:div.errors {:free-form/error-message {:key :radio-buttons}} [:p.error]]]
         [:button "Button"]]]
       [:h2 "Controls"]
       [layout/controls :reagent {:form-data data}]
       [layout/state @data]])))

(defmethod layout/pages :reagent-plain [_]
  [view])
