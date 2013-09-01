(ns tbpl-testtimes-scraper.core
  (:require [net.cgrand.enlive-html :as html]
            [clojure.string :as string]
            [cheshire.core :as json])
  (:gen-class :main true))

(defn load-page [path]
  (html/html-resource (java.io.File. path)))

(defn get-results [page]
  (html/select page [:ul.results]))


(defn get-platform-name [elem]
  (html/text (first (html/select elem [:span.os]))))

(defn get-nongrouped-results [elem]
  "Non-grouped (i.e. X, not M) results"
  (html/select elem [:span.osresults :> :a.machineResult]))

(defn get-grouped-results [elem]
  "Grouped (i.e. M) results"
  (html/select elem [:span.osresults :> :span.machineResultGroup]))

(defn get-minutes [title-str]
  "Mochitest 1 [...], took 33mins -> 33"
  (let [xmins (last (string/split title-str #" "))]
    (read-string (first (string/split xmins #"mins")))))

(defn parse-single-result [elem]
  {:name (html/text elem)
   :passed (= "success" (second (string/split (-> elem :attrs :class) #" ")))
   :duration (get-minutes (-> elem :attrs :title))})

(defn parse-grouped-result-structured [elem]
  {:name (-> elem :attrs :machinetype)
   :chunks (map parse-single-result (html/select elem [:a.machineResult]))})

(defn parse-grouped-result-flat [elem]
  (let [name (-> elem :attrs :machinetype)]
    (map #(assoc % :name (str name "-" (:name %))) (map parse-single-result (html/select elem [:a.machineResult])))))


(defn parse-platform-results [elem]
  {:platform (get-platform-name elem)
   :data (concat
          (map parse-single-result (get-nongrouped-results elem))
          (flatten (map parse-grouped-result-flat (get-grouped-results elem))))})

(defn parse-results [page]
  (map parse-platform-results (html/select (get-results page) [:li])))



(defn -main [& args]
  (if (not= (count args) 1)
    (println "Need to pass in path to local html page.")
    (println (json/generate-string
              (parse-results (load-page (first args)))
              {:pretty true}))))
