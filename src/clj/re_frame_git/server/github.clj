(ns re-frame-git.server.github
  (:require [clj-http.client :as client]
            [clojure.walk :refer [keywordize-keys]]))


(defn- build-api-url
  "Builds an api url using base github api url and endpoint path as argument"
  [endpoint]
  (str "https://api.github.com/" endpoint))

(defn github-api-request
  "Make a request to github api to endpoint arg"
  ([endpoint]
   (github-api-request endpoint {}))
  ([endpoint headers]
   (let [response
         (client/get (str "https://api.github.com/" endpoint)
           {:headers (merge
                       {:Authorization (str "token " (System/getenv "GITHUB_AUTH"))} 
                       {:accept (or
                                  (get headers "accept")
                                  "application/json")})})]
     (or (:body response) response))))
