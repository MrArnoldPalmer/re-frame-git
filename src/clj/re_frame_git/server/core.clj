(ns re-frame-git.server.core
  (:require [clojure.java.io :as io]
            [compojure.core :refer :all]
            [compojure.route :refer [resources]]
            [graph-router.core :refer :all]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [re-frame-git.server.posts :refer [save-post get-posts get-post-by-id]]))

(defn generate-data
  [_]
  {:Hello "Hello" :World "World"})

(def graph {(with :Root generate-data) [:Hello :World]})

(defn request-body
  [request]
  (-> request
      (:body)))
      ;(slurp)
      ;(read-string)))

(defn generate-response
  [body]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body body})

(defroutes server
  (GET "/" _
    {:status 200
     :headers {"Content-Type" "text/html; charset=utf-8"}
     :body (io/input-stream (io/resource "public/index.html"))})
  (context "/api" _
    (POST "/" request
      (let [data (dispatch graph (request-body request))]
        {:status 200
         :headers {"Content-Type" "application/edn"}
         :body data}))
    (POST "/posts" request
      (let [post (:body request)]
        (println post)
        (generate-response (save-post post))))
    (GET "/posts" _
      (generate-response (get-posts)))
    (GET "/posts/:id" [id]
      (generate-response (get-post-by-id id)))))

(defroutes app
  (-> server
      (wrap-keyword-params)
      (wrap-json-body {:keywords? true})
      (wrap-json-response)))
