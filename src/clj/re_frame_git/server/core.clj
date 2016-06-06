(ns re-frame-git.server.core
  (:require [clojure.java.io :as io]
            [compojure.core :refer :all]
            [ring.adapter.jetty :as ring]
            [ring.middleware.keyword-params :as keyword-params]
            [ring.middleware.json :as json]
            [ring.middleware.resource :as resource]
            [ring.middleware.content-type :as content-type]
            [ring.middleware.not-modified :as not-modified]
            [re-frame-git.server.github :refer [github-api-request]]))

(defn generate-response
  ([body]
   {:status 200
    :headers {"Content-Type" "application/json"}
    :body body})
  ([body headers]
   {:status 200
    :body body
    :headers headers}))

(defroutes server
  (GET "/" _
    {:status 200
     :headers {"Content-Type" "text/html; charset=utf-8"}
     :body (io/input-stream (io/resource "public/index.html"))})
  (context "/api" _
    (GET "/github/*" request
      (let [query-string (:query-string request)
            endpoint (str (get-in request [:params :*]) (if query-string (str "?" query-string) nil))
            headers (:headers request)]
        (if (= (get headers "accept") "application/vnd.github.VERSION.raw")
          (generate-response (github-api-request endpoint headers)
                             {"Content-Type" "application/vnd.github.VERSION.raw; charset=utf-8"})
          (generate-response (github-api-request endpoint headers)))))))

(defroutes app
  (-> server
      (keyword-params/wrap-keyword-params)
      (json/wrap-json-body {:keywords? true})
      (json/wrap-json-response)
      (resource/wrap-resource "public")
      (content-type/wrap-content-type)
      (not-modified/wrap-not-modified)))

(defn -main []
  (ring/run-jetty app {:port (Integer. (or (System/getenv "PORT") "8080"))
                       :join false}))
