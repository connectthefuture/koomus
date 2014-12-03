(ns bulk-loader.system
  (:require 
    [com.stuartsierra.component :as component]
    [bulk-loader.web-server :as web-server]
    [bulk-loader.queue :as queue]
    [bulk-loader.orchestrator :as orchestrator]
    [bulk-loader.logger :as logger]
    [koomus.trees.metrics :as metrics]
    [environ.core :as environ]))

(def components [:web-server :metrics :queue :orchestrator :logger])

(defrecord Bulk-Loader-System []
  component/Lifecycle
  (start [this]
    (component/start-system this components))
  (stop [this]
    (component/stop-system this components)))

(defn new-bulk-loader-system
  "Constructs a component system"
  []
  (map->Bulk-Loader-System
    {
     :web-server (component/using
                   (web-server/new-web-server)
                   {:queue :queue})
     :metrics (metrics/new-metrics (environ/env :graphite-host))
     :queue (queue/new-queue)
     :orchestrator (component/using
                     (orchestrator/new-orchestrator)
                     {:queue :queue})
     :logger (logger/new-logger)
    }))
