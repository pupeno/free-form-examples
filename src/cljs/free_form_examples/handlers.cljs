(ns free-form-examples.handlers
    (:require [re-frame.core :as re-frame]
              [free-form-examples.db :as db]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))
