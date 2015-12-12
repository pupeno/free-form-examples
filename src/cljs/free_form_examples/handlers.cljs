;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns free-form-examples.handlers
  (:require [re-frame.core :as re-frame]
            [free-form-examples.db :as db]))

(re-frame/register-handler
  :initialize-db
  (fn [_ _]
    db/default-db))

(re-frame/register-handler
  :update-re-frame-bootstrap
  (fn [db args]
    (println args)
    db))
