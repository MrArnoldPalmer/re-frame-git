(ns re-frame-git.server.core
  (:require [clojure.java.io :as io]
            [compojure.core :refer :all]
            [compojure.route :refer [resources]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [re-frame-git.server.posts :refer [save-post get-posts get-post-by-id]]
            [re-frame-git.server.github :refer [get-repositories get-repository-details]]))

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
    (POST "/posts" request
      (let [post (:body request)]
        (println post)
        (generate-response (save-post post))))
    (GET "/posts" _
      (generate-response (get-posts)))
    (GET "/posts/:id" [id]
      (generate-response (get-post-by-id id)))
    (GET "/github/repositories/:username" [username]
      (generate-response (get-repositories username)))
    (GET "/github/repository/:username/:repo-name" [username repo-name]
      (generate-response (get-repository-details username repo-name)))))

(defroutes app
  (-> server
      (wrap-keyword-params)
      (wrap-json-body {:keywords? true})
      (wrap-json-response)))
