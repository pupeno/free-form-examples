;;;; Copyright © 2015, 2016 José Pablo Fernández Silva, All rights reserved.

(ns free-form-examples.forms.re-frame.plain
  (:require [reagent.ratom :as ratom :include-macros true]
            [re-frame.core :as re-frame]
            [free-form.re-frame :as free-form]
            [free-form-examples.layout :as layout]))

(re-frame/register-sub
  :re-frame-plain
  (fn [db]
    (ratom/reaction (:re-frame-plain @db))))

(re-frame/register-handler
  :update-re-frame-plain
  (fn [db [_ keys value :as event]]
    (-> db
        (assoc-in (cons :re-frame-plain keys) value)
        (update :event-log #(conj % event)))))

(defmethod layout/pages :re-frame-plain [_]
  (let [data (re-frame/subscribe [:re-frame-plain])]
    (fn []
      [:div
       [layout/source-code-button "re_frame/plain.cljs"]
       [:h1 "Re-frame"]
       [free-form/form {} {} :update-re-frame-plain
        [:form {:noValidate true}
         [:div.errors {:free-form/error-message {:key :-general}} [:p.error]]
         [:div.field {:free-form/error-class {:key :text :error "has-error"}}
          [:label {:for :text} "Text"]
          [:input.form-control {:free-form/input {:key :text}
                                :type            :text
                                :id              :text
                                :placeholder     "placeholder@example.com"}]
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
                                :id              :password
                                :placeholder     "placeholder@example.com"}]
          [:div.errors {:free-form/error-message {:key :password}} [:p.error]]]
         [:div.field {:free-form/error-class {:key :select :error "has-error"}}
          [:label {:for :select} "Select"]
          [:select.form-control {:free-form/input {:key :select}
                                 :type            :select
                                 :id              :select}
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
         [:button "Button"]]]
       [layout/state @data :re-frame-plain]
       [layout/event-log]])))
