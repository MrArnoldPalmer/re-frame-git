(ns re-frame-git.server.github
  (:require [clj-http.client :as client]
            [environ.core :refer [env]]))


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
                      {:Authorization (str "token " (env :github-auth))} 
                      {:accept (or
                                 (get headers "accept")
                                 "application/json")})
           :throw-exceptions false})]
    (if (= (:status response) 404)
      nil
      (or (:body response) response)))))
