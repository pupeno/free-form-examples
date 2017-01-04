;;;; Copyright © 2015-2017 José Pablo Fernández Silva

(ns free-form-examples.db
  (:require [re-frame.core :as re-frame]
            [reagent.ratom :as ratom :include-macros true]
            [ajax.core :as ajax]))

(def default-db
  {:current-route                   nil
   :re-frame-plain                  {}
   :re-frame-bootstrap-3            {}
   :re-frame-bootstrap-3-horizontal {}
   :re-frame-bootstrap-3-inline     {}
   :validation-error                {}
   :event-log                       ()
   :readme                          nil})

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

(re-frame/reg-sub :readme
  (fn [db]
    (:readme db)))

(re-frame/reg-event-db :request-readme
  (fn [db _]
    (when (nil? (:readme db))
      (ajax/GET "https://raw.githubusercontent.com/pupeno/free-form/master/README.md"
                {:handler #(re-frame/dispatch [:render-readme %])}))
    db))

(re-frame/reg-event-db :render-readme
  (fn [db [_ raw-readme]]
    (ajax/POST "https://api.github.com/markdown"
               {:params  {:text raw-readme}
                :format  (ajax/json-request-format)
                :handler #(re-frame/dispatch [:load-readme %])})
    db))

(re-frame/reg-event-db :load-readme
  (fn [db [_ rendered-readme]]
    (assoc db :readme rendered-readme)))
