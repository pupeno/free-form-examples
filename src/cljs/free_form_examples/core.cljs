;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns free-form-examples.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [free-form-examples.layout :as layout]
            [free-form-examples.config :as config]
            [free-form-examples.routing :as routing]
            free-form-examples.db
            free-form-examples.forms.reagent.plain
            free-form-examples.forms.reagent.bootstrap-3
            free-form-examples.forms.reagent.bootstrap-3-horizontal
            free-form-examples.forms.reagent.bootstrap-3-inline
            free-form-examples.forms.re-frame.plain
            free-form-examples.forms.re-frame.bootstrap-3
            free-form-examples.forms.re-frame.bootstrap-3-horizontal
            free-form-examples.forms.re-frame.bootstrap-3-inline))

(when config/debug?
  (println "dev mode"))

(defn mount-root []
  (reagent/render [layout/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (routing/start!)
  (mount-root))
