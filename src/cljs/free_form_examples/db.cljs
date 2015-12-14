;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns free-form-examples.db
  (:require [re-frame.core :as re-frame]
            [reagent.ratom :as ratom :include-macros true]))

(def default-db
  {:current-route                 nil
   :re-frame-plain                {}
   :re-frame-bootstrap-3            {}
   :re-frame-bootstrap-3-horizontal {}
   :re-frame-bootstrap-3-inline     {}
   :event-log                     ()})

(re-frame/register-handler
  :initialize-db
  (fn [_ _]
    default-db))

(re-frame/register-sub
  :db
  (fn [db]
    (ratom/reaction @db)))

(re-frame/register-sub
  :event-log
  (fn [db]
    (ratom/reaction (:event-log @db))))
