(ns re-frame-git.server.github
  (:require [clj-http.client :as client]))


(defn build-api-url
  "Builds an api url using base github api url and endpoint path as argument"
  [endpoint]
  (str "https://api.github.com/" endpoint))

(defn github-api-request
  "Make a request to github api to endpoint arg"
  [endpoint]
  (:body (client/get (str "https://api.github.com/" endpoint)
           {:headers {:Authorization (str "token " (System/getenv "GITHUB_AUTH"))}})))

(defn get-repositories
  [username]
  (:body (client/get (build-api-url (str "/users/" username "/repos")))))

(defn get-repository-details
  [username repo-name]
  (:body (client/get (build-api-url (str "/repos/" username "/" repo-name)))))
