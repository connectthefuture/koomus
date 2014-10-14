(defproject bulk-loader "0.1.0-SNAPSHOT"
  :description "Loads large images into Neo4j"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.neo4j/neo4j "2.1.5"]
                 [environ "1.0.0"]
                 [robert/hooke "1.3.0"]
                 [org.neo4j/neo4j "1.9"]
                 [com.taoensso/timbre "3.3.1"]
                 [neo4j-batch-inserter "0.1.0-SNAPSHOT"]
                 [cheshire "5.3.1"]
                 [com.stuartsierra/component "0.2.2"]
                 [compojure "1.2.0"]]
  :main bulk-loader.core
  :profiles {:dev {:plugins [[lein-midje "3.1.3" ]
                             [lein-ancient "0.5.5"]
                             [lein-kibit "0.0.8"]
                             [lein-bikeshed "0.1.8"]
                             [jonase/eastwood "0.1.4"]]
                   :dependencies [[midje "1.6.3"]]}}
;  :global-vars  {*warn-on-reflection* true
;                 *assert* false}
  :test-selectors  {:default  (complement :benchmark)
                    :benchmark :benchmark
                    :all  (constantly true)}
  :resource-paths  ["test/resources"]
  :aliases  {"omni"  ["do"  ["clean"] ["ancient"]  ["kibit"]  ["bikeshed"] ["eastwood"]]}
  :jvm-opts  ["-Xms2g" "-Xmx12g"])