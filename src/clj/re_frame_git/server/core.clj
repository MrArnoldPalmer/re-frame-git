(ns re-frame-git.server.core
  (:require [clojure.java.io :as io]
            [compojure.core :refer :all]
            [compojure.route :refer [resources]]
            [graph-router.core :refer :all]))

(defn generate-data
  [_]
  {:Hello "Hello" :World "World"})

(def graph {(with :Root generate-data) [:Hello :World]})

(defroutes server
  (GET "/" _
    {:status 200
     :headers {"Content-Type" "text/html; charset=utf-8"}
     :body (io/input-stream (io/resource "public/index.html"))})
  (POST "/api" request
    (println-str request)
    {:status 200
     :body (dispatch graph {:Root [:Hello :World]})}))

