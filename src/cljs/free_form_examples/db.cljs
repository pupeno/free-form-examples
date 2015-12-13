;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns free-form-examples.db
  (:require [re-frame.core :as re-frame]))

(def default-db
  {:current-route                 nil
   :re-frame-plain                {}
   :re-frame-bootstrap            {}
   :re-frame-bootstrap-horizontal {}
   :re-frame-bootstrap-inline     {}})

(re-frame/register-handler
  :initialize-db
  (fn [_ _]
    default-db))
