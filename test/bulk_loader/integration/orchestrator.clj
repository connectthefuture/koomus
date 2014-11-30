(ns bulk-loader.integration.orchestrator
  (:use [midje.sweet :only (facts fact => just anything)])
  (:require [clojure.test :refer :all]
            [bulk-loader.orchestrator :refer :all]))

(fact :slow "a node should exist for each of the (* 512 512 220) pixels in the image"
      (let [path (str (System/getProperty "user.dir") "/resources/IMG00000")
            result (flatten (load-dicom path 3))]
        (reduce + result) => 786432))