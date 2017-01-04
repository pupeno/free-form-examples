;;;; Copyright © 2015-2017 José Pablo Fernández Silva

(ns free-form-examples.db
  (:require [re-frame.core :as re-frame]
            [reagent.ratom :as ratom :include-macros true]))

(def default-db
  {:current-route                   nil
   :re-frame-plain                  {}
   :re-frame-bootstrap-3            {}
   :re-frame-bootstrap-3-horizontal {}
   :re-frame-bootstrap-3-inline     {}
   :validation-error                {}
   :event-log                       ()})

(re-frame/reg-event-db
  :initialize-db
  (fn [_ _]
    default-db))

(re-frame/reg-sub-raw
  :db
  (fn [db]
    (ratom/reaction @db)))

(re-frame/reg-sub-raw
  :event-log
  (fn [db]
    (ratom/reaction (:event-log @db))))
