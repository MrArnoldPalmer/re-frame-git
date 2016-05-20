(ns re-frame-git.server.core
  (:require [clojure.java.io :as io]
            [compojure.core :refer :all]
            [compojure.route :refer [resources]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [re-frame-git.server.posts :refer [save-post get-posts get-post-by-id]]
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
    (POST "/posts" request
      (let [post (:body request)]
        (println post)
        (generate-response (save-post post))))
    (GET "/posts" _
      (generate-response (get-posts)))
    (GET "/posts/:id" [id]
      (generate-response (get-post-by-id id)))
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
      (wrap-keyword-params)
      (wrap-json-body {:keywords? true})
      (wrap-json-response)))
