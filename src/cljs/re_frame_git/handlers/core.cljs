(ns re-frame-git.handlers.core
  (:require [re-frame.core :as re-frame :refer [trim-v debug]]
            [re-frame-git.db :as db]
            [re-frame-git.config :as config]
            [re-frame-git.handlers.repo-handlers :as repo-handlers]
            [re-frame-git.handlers.post-handlers :as post-handlers]))

(defn register-handler
  [keyword handler & middleware]
  (let [universal (concat (if config/debug? [debug] []) [trim-v])
        middleware (apply comp universal middleware)]
    (re-frame/register-handler keyword middleware handler)))

(defn set-current-route
  [db [route]]
  (assoc-in db [:current-route] route))

(defn api-error
  [db [response loading-flag-vector]]
  (println "An API error has occured")
  (println (:error-text response))
  (assoc-in db loading-flag-vector false))

(register-handler
  :initialize-db
  (fn []
    db/default-db))
(register-handler :set-current-route set-current-route)
(register-handler :get-posts post-handlers/get-posts)
(register-handler :set-repo-list repo-handlers/set-repo-list)
(register-handler :get-repo-list repo-handlers/get-repo-list)
(register-handler :process-repo-list-response repo-handlers/process-repo-list-response)
(register-handler :process-posts-response post-handlers/process-posts-response)
(register-handler :api-error api-error)
(register-handler :get-repo repo-handlers/get-repo)
(register-handler :process-repo-response repo-handlers/process-repo-response)
(register-handler :get-repo-languages repo-handlers/get-repo-languages)
(register-handler :process-repo-languages-response repo-handlers/process-repo-languages-response)
(register-handler :get-repo-branches repo-handlers/get-repo-branches)
(register-handler :process-repo-branches-response repo-handlers/process-repo-branches-response)
(register-handler :get-repo-tree repo-handlers/get-repo-tree)
(register-handler :process-repo-tree-response repo-handlers/process-repo-tree-response)
