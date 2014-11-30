(defproject bulk-loader "0.1.0-SNAPSHOT"
  :description "Converting images into images"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [environ "1.0.0"]
                 [robert/hooke "1.3.0"]
                 [clj-logging-config "1.9.12"]
                 [com.taoensso/timbre "3.3.1"]
                 [cheshire "5.3.1"]
                 [com.stuartsierra/component "0.2.2"]
                 [compojure "1.2.0"]
                 [ring/ring-jetty-adapter "1.3.1"]
                 [ring/ring-json "0.3.1"]
                 [ring/ring-defaults "0.1.2"]
                 [org.clojars.hozumi/clj-commons-exec "1.0.7"]
                 [org.clojars.s450r1/dcm4che-imageio "2.0.23"]
                 [org.clojars.s450r1/dcm4che-image "2.0.23"]
                 [org.clojars.s450r1/dcm4che-core "2.0.23"]
                 [org.clojars.s450r1/dcm4che-net "2.0.23"]
                 [javax.media/jai_imageio "1.1"]
                 [com.revelytix.logbacks/slf4j-log4j12 "1.0.0"]
                 [clj-http "1.0.1"]
                 [cheshire "5.3.1"]
                 [koomus.trees "0.1.14-SNAPSHOT"]
                 [org.clojure/core.memoize "0.5.6"]]
  :profiles {:dev {:env {:host "127.0.0.1"
                         :port 1245
                         :graphite-host "127.0.0.1"}
                   :source-paths  ["dev"]
                   :plugins [[lein-midje "3.1.3" ]
                             [lein-ancient "0.5.5"]
                             [lein-kibit "0.0.8"]
                             [lein-bikeshed "0.1.8"]
                             [jonase/eastwood "0.1.4"]
                             [lein-environ "1.0.0"]]
                   :dependencies [[midje "1.6.3"]
                                  [org.clojure/tools.namespace "0.2.7"]]}
             :uberjar {:aot :all}}
  :main bulk-loader.zygote
  :aliases {"omni" ["do" ["clean"] ["ancient"] ["kibit"] ["bikeshed"] ["eastwood"]]}
  :jvm-opts  ["-Xms2g" "-Xmx8g"]
  :repositories [["imageio" "http://maven.geotoolkit.org/"]])
