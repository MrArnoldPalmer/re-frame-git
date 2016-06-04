(ns re-frame-git.handlers
  (:require [re-frame.core :as re-frame]
            [re-frame-git.db :as db]
            [re-frame-git.config :as config]
            [re-frame-git.middleware :refer [repo-loaded]]
            [re-frame-git.repositories.handlers :as repositories]
            [re-frame-git.repo-details.handlers :as repo-details]))

(defn register-handler
  [handler-keyword handler-function & middleware]
  (re-frame/register-handler
    handler-keyword
    (apply comp
           (concat
             (if config/debug? [re-frame/debug] [])
             [re-frame/trim-v]
             (if (nil? middleware) [] middleware)))
    handler-function))

(register-handler
  :set-current-route
  (fn
    [db [route]]
    (assoc-in db [:current-route] route)))

(defn api-error
  [db [response loading-flag-vector]]
  (println "An API error has occured")
  (println (:error-text response))
  (if (nil? loading-flag-vector)
    db
    (assoc-in db loading-flag-vector false)))

(register-handler :api-error api-error)
(register-handler :initialize-db (fn [] db/default-db))
(register-handler :set-repo-list repositories/set-repo-list)
(register-handler :get-repo-list repositories/get-repo-list)
(register-handler :process-repo-list-response repositories/process-repo-list-response)
(register-handler :load-repo-details repo-details/load-repo-details)
(register-handler :set-current-repo repo-details/set-current-repo)
(register-handler :get-repo repo-details/get-repo)
(register-handler :process-repo-response repo-details/process-repo-response repo-loaded)
(register-handler :get-repo-readme repo-details/get-repo-readme)
(register-handler :process-repo-readme-response repo-details/process-repo-readme-response repo-loaded)
(register-handler :get-repo-languages repo-details/get-repo-languages)
(register-handler :process-repo-languages-response repo-details/process-repo-languages-response repo-loaded)
(register-handler :get-repo-branches repo-details/get-repo-branches)
(register-handler :process-repo-branches-response repo-details/process-repo-branches-response repo-loaded)
(register-handler :get-repo-tree repo-details/get-repo-tree)
(register-handler :process-repo-tree-response repo-details/process-repo-tree-response repo-loaded)
