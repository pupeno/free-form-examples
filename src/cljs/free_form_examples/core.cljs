(ns free-form-examples.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [free-form-examples.handlers]
              [free-form-examples.subs]
              [free-form-examples.views :as views]
              [free-form-examples.config :as config]))

(when config/debug?
  (println "dev mode"))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init [] 
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
