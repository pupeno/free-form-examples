;;;; Copyright © 2015-2017 José Pablo Fernández Silva

(ns free-form-examples.config)

(def debug?
  ^boolean js/goog.DEBUG)

(when debug?
  (enable-console-print!))
