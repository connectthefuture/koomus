(ns bulk-loader.infra.logger
  (:require 
    [com.stuartsierra.component :as component]
    [clj-logging-config.log4j :as log-config]
    [clojure.tools.logging :as log]
    [bulk-loader.etl.extractor :as extractor]
    [bulk-loader.etl.loader :as loader]
    [robert.hooke :as hooke]
    [cheshire.core :as json]))

(def app-name {:appname "bulk-loader"})
(def uncached (merge {:action "uncached-access"} app-name))
(def cached (merge {:action "cached-access"} app-name))
(def send-message (merge {:action "send-message"} app-name))

(defn- log-send-msg
  [f data slice-id]
  (let [msg {:msg (format "message %1s sent with %2s pixels" slice-id (count data))}]
    (-> msg
        (merge send-message)
        json/generate-string
        log/debug) 
    (f data slice-id)))

(defn- log-uncached-access
  [f name args]
  (let [msg (merge uncached {:msg name})
        json-msg (json/generate-string msg)]
    (log/debug json-msg)
    (apply f args)))

(defn- log-cached-access
  [f name args]
  (let [msg (merge cached {:msg name})
        json-msg (json/generate-string msg)]
    (log/debug json-msg)
    (apply f args)))

(defn- log-uncached-build-pixels
  [f & args]
  (log-uncached-access f "bulk-loader.extractor/build-pixels" args))

(defn- log-cached-build-pixels
  [f & args]
  (log-cached-access f "bulk-loader.extractor/build-pixels-fifo" args))

(defn- log-uncached-order-files-by-slice
  [f & args]
  (log-uncached-access f "bulk-loader.extractor/order-files-by-slice" args))

(defn- log-cached-order-files-by-slice
  [f & args]
  (log-cached-access f "bulk-loader.extractor/order-files-by-slice-fifo" args))

(defn- start-logger
  []
  (log-config/set-logger!)
  (hooke/add-hook #'extractor/order-files-by-slice #'log-uncached-order-files-by-slice)
  (hooke/add-hook #'extractor/build-pixels #'log-uncached-build-pixels)
  (hooke/add-hook #'extractor/order-files-by-slice-fifo #'log-cached-order-files-by-slice)
  (hooke/add-hook #'extractor/build-pixels-fifo #'log-cached-build-pixels)
  (hooke/add-hook #'loader/send-msg #'log-send-msg))

(defrecord Logger []
  component/Lifecycle
  (start [this]
    (start-logger)
    this)
  (stop [this]
    this))

(defn new-logger []
  (map->Logger {}))
