;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns free-form-examples.views
  (:require [re-frame.core :as re-frame]
            [free-form.core :as ff-core]
            [free-form.re-frame :as ff-re-frame]))

(defn main-panel []
  (let [re-frame-bootstrap (re-frame/subscribe [:re-frame-bootstrap])]
    (fn []
      [:div
       [:h1 "Re-frame + Bootstrap"]
       [:p "These example use Free-form Bootstrap support that allows you to have very sucint code."]
       [ff-re-frame/form {} {} :update-re-frame-bootstrap
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
       [:h2 "Horizontal"]
       [ff-re-frame/form {} {} :update-re-frame-bootstrap
        [:form.form-horizontal {:noValidate        true
                                :free-form/options {:mode :bootstrap}
                                #_:free-form/renderer #_{:name        :bootstrap
                                                         :label-width 2
                                                         :value-width 10}}
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
           [:button.btn.btn-primary {:type :submit} "Button"]]]]]
       [:h2 "Inline"]
       [ff-re-frame/form {} {} :update-re-frame-bootstrap
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
       [:hr]
       [:h1 "Re-frame"]
       [ff-re-frame/form {} {} :update-re-frame
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
         [:button "Button"]]]
       [:hr]
       [:h1 "Reagent + Bootstrap"]
       [:p "These example use Free-form Bootstrap support that allows you to have very sucint code."]
       [ff-core/form {} {} (fn [& args] (println args))
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
         [:div.form-group
          [:div.col-sm-offset-2.col-sm-5
           [:button.btn.btn-primary {:type :submit}
            "Button"]]]]]
       [:h2 "Horizontal"]
       [ff-core/form {} {} (fn [& args] (println args))
        [:form.form-horizontal {:noValidate        true
                                :free-form/options {:mode :bootstrap}
                                #_:free-form/renderer #_{:name        :bootstrap
                                                         :label-width 2
                                                         :value-width 10}}
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
            "Button"]]]]]
       [:h2 "Inline"]
       [ff-core/form {} {} (fn [& args] (println args))
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
       [:hr]
       [:h1 "Reagent"]
       [ff-core/form {} {} (fn [& args] (println args))
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
         [:button "Button"]]]])))
