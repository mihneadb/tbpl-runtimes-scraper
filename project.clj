(defproject tbpl-testtimes-scraper "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Mozilla Public License"
            :url "http://www.mozilla.org/MPL/2.0/"}
  :main tbpl-testtimes-scraper.core
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [enlive "1.1.4"]
                 [cheshire "5.2.0"]]
  :uberjar-name "tbpl-testtimes.jar"
  :aot :all)
