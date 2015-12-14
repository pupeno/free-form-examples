(defproject free-form-examples "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [reagent "0.5.1"]
                 [re-frame "0.6.0"]
                 [com.domkm/silk "0.1.1"]
                 [kibu/pushy "0.3.6"]
                 [com.carouselapps/free-form "0.2.0-SNAPSHOT"]]

  :description "Examples of how to use the form library Free-form"

  :url "https://carouselapps.com/free-form"

  :license {:name "Eclipse Public License v1.0"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.0-2"]
            [lein-ring "0.9.7"]]
  :ring {:handler free-form-examples.core/app}

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs     ["resources/public/css"]
             :ring-handler free-form-examples.core/app}

  :cljsbuild {:builds [{:id           "dev"
                        :source-paths ["src/cljs"]          ; Add "checkouts/free-form/src/cljs" to make it easy to work on Free-frame and re-load code.

                        :figwheel     {:on-jsload "free-form-examples.core/mount-root"}

                        :compiler     {:main                 free-form-examples.core
                                       :output-to            "resources/public/js/compiled/app.js"
                                       :output-dir           "resources/public/js/compiled/out"
                                       :asset-path           "/js/compiled/out"
                                       :source-map-timestamp true}}

                       {:id           "min"
                        :source-paths ["src/cljs"]
                        :compiler     {:main            free-form-examples.core
                                       :output-to       "resources/public/js/compiled/app.js"
                                       :optimizations   :advanced
                                       :closure-defines {goog.DEBUG false}
                                       :pretty-print    false}}]})
