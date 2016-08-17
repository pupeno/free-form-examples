;;;; Copyright © 2015, 2016 José Pablo Fernández Silva, All rights reserved.

(ns free-form-examples.forms.reagent.plain
  (:require [reagent.core :as reagent]
            [free-form.core :as free-form]
            [free-form-examples.layout :as layout]))

(defmethod layout/pages :reagent-plain [_]
  (let [data (reagent/atom {})]
    (fn [_]
      [:div
       [layout/source-code-button "reagent/plain.cljs"]
       [:h1 "Reagent"]
       [free-form/form @data (:-errors @data) (fn [keys value] (swap! data #(assoc-in % keys value)))
        [:form {:noValidate true}
         [:div.errors {:free-form/error-message {:key :-general}} [:p.error]]
         [:div.field {:free-form/error-class {:key :text :error "has-error"}}
          [:label {:for :text} "Text"]
          [:input.form-control {:free-form/input {:key :text}
                                :type            :text
                                :id              :text
                                :placeholder     "placeholder"}]
          [:div.errors {:free-form/error-message {:key :text}} [:p.error]]]
         [:div.field {:free-form/error-class {:key :email :error "has-error"}}
          [:label {:for :email} "Email"]
          [:input.form-control {:free-form/input {:key :email}
                                :type            :email
                                :id              :email
                                :placeholder     "placeholder@example.com"}]
          [:div.errors {:free-form/error-message {:key :email}} [:p.error]]]
         [:div.field {:free-form/error-class {:key :password :error "has-error"}}
          [:label {:for :password} "Password"]
          [:input.form-control {:free-form/input {:key :password}
                                :type            :password
                                :id              :password}]
          [:div.errors {:free-form/error-message {:key :password}} [:p.error]]]
         [:div.field {:free-form/error-class {:key :select :error "has-error"}}
          [:label {:for :select} "Select"]
          [:select.form-control {:free-form/input {:key :select}
                                 :type            :select
                                 :id              :select}
           [:option]
           [:option {:value :dog} "Dog"]
           [:option {:value :cat} "Cat"]
           [:option {:value :squirrel} "Squirrel"]
           [:option {:value :giraffe} "Giraffe"]]
          [:div.errors {:free-form/error-message {:key :select}} [:p.error]]]
         [:div.field {:free-form/error-class {:key :select :error "has-error"}}
          [:label {:for :select} "Select with groups"]
          [:select.form-control {:free-form/input {:key :select-with-group}
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
         [:div.field {:free-form/error-class {:key :text :error "has-error"}}
          [:label {:for :text-area} "Text area"]
          [:textarea.form-control {:free-form/input {:key :textarea}
                                   :id              :textarea}]
          [:div.errors {:free-form/error-message {:key :textarea}} [:p.error]]]
         [:div.field {:free-form/error-class {:key [:t :e :x :t] :error "has-error"}}
          [:label {:for :text} "Text with deep keys"]
          [:input.form-control {:free-form/input {:keys [:t :e :x :t]}
                                :type            :text
                                :id              :text
                                :placeholder     "placeholder"}]
          [:div.errors {:free-form/error-message {:keys [:t :e :x :t]}} [:p.error]]]
         [:button "Button"]]]
       [layout/state @data]])))
