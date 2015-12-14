;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns free-form-examples.forms.reagent.plain
  (:require [free-form.core :as free-form]
            [free-form-examples.layout :as layout]))

(defmethod layout/pages :reagent-plain [_]
  (fn []
    [:div
     [layout/source-code-button "reagent/plain.cljs"]
     [:h1 "Reagent"]
     [free-form/form {} {} (fn [& args] (println args))
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
       [:button "Button"]]]]))
