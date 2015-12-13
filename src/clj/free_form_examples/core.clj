;;;; Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

(ns free-form-examples.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as response]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (route/resources "/")
  (rfn [] (response/content-type (response/resource-response "index.html" {:root "public"}) "text/html")))

(def app
  (wrap-defaults app-routes site-defaults))
