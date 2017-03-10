;;;; Copyright © 2015-2017 José Pablo Fernández Silva

(defproject free-form-examples "0.1.0-SNAPSHOT"
  :description "Examples of how to use the form library Free-form"
  :url "https://github.com/pupeno/free-form"
  :license {:name "Eclipse Public License v1.0"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj"]

  :cljsbuild {:builds {:app {:source-paths ["src/cljs"]
                             :compiler     {:main       free-form-examples.core
                                            :output-to  "resources/public/js/app.js"
                                            :output-dir "resources/public/js"
                                            :asset-path "/js"}}}}

  :sass {:source-paths ["resources/styles"]
         :target-path  "resources/public/css"}

  :ring {:handler free-form-examples.core/app}

  :clean-targets ^{:protect false} ["resources/public/js"
                                    "resources/public/css"
                                    "target"]

  :uberjar-name "free-form-examples-standalone.jar"
  :heroku {:app-name "free-form-examples"}

  :profiles {:dev        {:cljsbuild    {:builds {:app {:source-paths ["checkouts/free-form/src/cljs"]
                                                        :compiler     {:preloads             [devtools.preload]
                                                                       :source-map           true
                                                                       :pretty-print         true
                                                                       :optimizations        :none
                                                                       :source-map-timestamp true
                                                                       :external-config      {:devtools/config {:features-to-install :all}}}
                                                        :figwheel     {:on-jsload "free-form-examples.core/mount-root"}}}}
                          :dependencies [[figwheel-sidecar "0.5.0"]
                                         [binaryage/devtools "0.9.0"]
                                         [com.cemerick/piggieback "0.2.1"]
                                         [org.clojure/tools.nrepl "0.2.12"]
                                         [org.slf4j/slf4j-nop "1.7.13"]]
                          :figwheel     {:css-dirs       ["resources/public/css"]
                                         :server-logfile "log/figwheel-logfile.log"
                                         :ring-handler   free-form-examples.core/app}}
             :production {:env {:production "true"}}
             :uberjar    {:omit-source true
                          :aot         :all
                          :env         {:production "true"}
                          :hooks       [leiningen.cljsbuild]
                          :prep-tasks  ["javac"
                                        "compile"
                                        #_["sass4clj" "once"] ; This should be the correct way of compiling SCSS, but: https://github.com/Deraen/sass4clj/issues/18
                                        ["cljsbuild" "once"]
                                        ["shell" "lein" "sass4clj" "once"]]
                          :cljsbuild   {:jar    true
                                        :builds {:app {:compiler {:optimizations   :advanced
                                                                  :closure-defines {goog.DEBUG false}
                                                                  :pretty-print    false}}}}}}

  :min-lein-version "2.5.3"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]
                 [compojure "1.5.1"]
                 [ring/ring-defaults "0.2.1"]
                 [ring/ring-jetty-adapter "1.5.0"]
                 [environ "1.1.0"]
                 [reagent "0.6.0"]
                 [re-frame "0.8.0"]
                 [com.domkm/silk "0.1.2"]
                 [kibu/pushy "0.3.6"]
                 [binaryage/devtools "0.8.3"]
                 [cljs-ajax "0.5.8"]
                 [com.pupeno/free-form "0.6.0-SNAPSHOT"]]
  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-figwheel "0.5.4-7"]
            [deraen/lein-sass4clj "0.3.0"]
            [lein-heroku "0.5.3"]])
