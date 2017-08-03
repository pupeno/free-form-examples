;;;; Copyright © 2015-2017 José Pablo Fernández Silva

(defproject free-form-examples "0.1.0-SNAPSHOT"
  :description "Examples of how to use the form library Free-form"
  :url "https://github.com/pupeno/free-form"
  :license {:name "Eclipse Public License v1.0"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj"]

  :cljsbuild {:builds {:app {:source-paths ["src/cljs"]
                             :compiler     {:main          free-form-examples.core
                                            :output-to     "resources/public/js/app.js"
                                            :output-dir    "resources/public/js"
                                            :asset-path    "/js"
                                            :optimizations :none}}}}

  :sass {:source-paths ["resources/styles"]
         :target-path  "resources/public/css"}

  :clean-targets ^{:protect false} ["resources/public/js"
                                    "resources/public/css"
                                    "target"]

  :uberjar-name "free-form-examples-standalone.jar"
  :heroku {:app-name "free-form-examples"}

  :shell {:commands {"lein" {:windows ["cmd.exe" "/c" "lein.bat"]}}}

  :profiles {:dev     {:cljsbuild    {:builds {:app {:source-paths ["checkouts/free-form/src/cljs"]
                                                     :compiler     {;:preloads             [devtools.preload]
                                                                    :source-map           true
                                                                    :source-map-timestamp true
                                                                    :pretty-print         true
                                                                    :external-config      {:devtools/config {:features-to-install :all}}}
                                                     :figwheel     {:on-jsload "free-form-examples.core/mount-root"}}}}
                       :dependencies [[figwheel-sidecar "0.5.9"]
                                      ;[binaryage/devtools "0.9.2"]
                                      [com.cemerick/piggieback "0.2.1"]
                                      [org.clojure/tools.nrepl "0.2.12"]
                                      [org.slf4j/slf4j-nop "1.7.24"]]
                       :figwheel     {:css-dirs       ["resources/public/css"]
                                      :server-logfile "log/figwheel-logfile.log"
                                      :ring-handler   free-form-examples.core/app}
                       :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}
             :uberjar {:omit-source true
                       :aot         :all
                       :env         {:production "true"}
                       :hooks       [leiningen.cljsbuild]
                       :prep-tasks  ["javac"
                                     "compile"
                                     ["cljsbuild" "once"]
                                     #_["sass4clj" "once"]  ; This should be the correct way of compiling SCSS, but: https://github.com/Deraen/sass4clj/issues/18
                                     ["shell" "lein" "sass4clj" "once"]]
                       :cljsbuild   {:jar    true
                                     :builds {:app {:compiler {:closure-defines {goog.DEBUG false}}}}}}}

  :min-lein-version "2.5.3"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.494"]
                 [compojure "1.5.2"]
                 [ring/ring-defaults "0.2.3"]
                 [ring/ring-jetty-adapter "1.5.1"]
                 [environ "1.1.0"]
                 [cheshire "5.7.0"]                         ; Required by sass4clj
                 [cljsjs/react-with-addons "15.4.2-2"]
                 [reagent "0.6.1" :exclusions [cljsjs/react]]
                 [re-frame "0.8.0"]
                 [com.domkm/silk "0.1.2"]
                 [kibu/pushy "0.3.6"]
                 [cljs-ajax "0.5.8"]
                 [camel-snake-kebab "0.4.0"]
                 [tech.dashman/reagent-toolbox "0.1.0-SNAPSHOT"]
                 [com.pupeno/free-form "0.6.0-SNAPSHOT"]]

  :plugins [[lein-cljsbuild "1.1.4"]
            ;[lein-figwheel "0.5.9"]
            [deraen/lein-sass4clj "0.3.0"]
            [lein-heroku "0.5.3"]
            [lein-shell "0.5.0"]])
