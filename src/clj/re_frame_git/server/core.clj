(ns re-frame-git.server.core
  (:require [clojure.java.io :as io]
            [compojure.core :refer :all]
            [compojure.route :refer [resources]]
            [graph-router.core :refer :all]))

(defn generate-data
  [_]
  {:Hello "Hello" :World "World"})

(def graph {(with :Root generate-data) [:Hello :World]})

(defn request-query
  [request]
  (-> request
      (:body)
      (slurp)
      (read-string)))

(defroutes handler
  (GET "/" _
    {:status 200
     :headers {"Content-Type" "text/html; charset=utf-8"}
     :body (io/input-stream (io/resource "public/index.html"))})
  (POST "/api" request
    (let [data (dispatch graph (request-query request))]
      (println data)
      {:status 200
       :body data})))
