(ns re-frame-git.handlers.core
  (:require [re-frame.core :as re-frame :refer [trim-v debug]]
            [re-frame-git.db :as db]
            [re-frame-git.config :as config]
            [re-frame-git.middleware.core :refer [repo-loaded]]
            [re-frame-git.handlers.repo-handlers :as repo-handlers]))

(defn register-handler
  [handler-keyword handler-function & middleware]
  (re-frame/register-handler
    handler-keyword
    (apply comp
           (concat
             (if config/debug? [debug] [])
             [trim-v]
             (if (nil? middleware) [] middleware)))
    handler-function))

(defn set-current-route
  [db [route]]
  (assoc-in db [:current-route] route))

(defn api-error
  [db [response loading-flag-vector]]
  (println "An API error has occured")
  (println (:error-text response))
  (if (nil? loading-flag-vector)
    db
    (assoc-in db loading-flag-vector false)))

(register-handler :initialize-db (fn [] db/default-db))
(register-handler :set-current-route set-current-route)
(register-handler :set-repo-list repo-handlers/set-repo-list)
(register-handler :get-repo-list repo-handlers/get-repo-list)
(register-handler :process-repo-list-response repo-handlers/process-repo-list-response)
(register-handler :load-repo-details repo-handlers/load-repo-details)
(register-handler :api-error api-error)
(register-handler :set-current-repo repo-handlers/set-current-repo)
(register-handler :get-repo repo-handlers/get-repo)
(register-handler :process-repo-response repo-handlers/process-repo-response repo-loaded)
(register-handler :get-repo-readme repo-handlers/get-repo-readme)
(register-handler :process-repo-readme-response repo-handlers/process-repo-readme-response repo-loaded)
(register-handler :get-repo-languages repo-handlers/get-repo-languages)
(register-handler :process-repo-languages-response repo-handlers/process-repo-languages-response repo-loaded)
(register-handler :get-repo-branches repo-handlers/get-repo-branches)
(register-handler :process-repo-branches-response repo-handlers/process-repo-branches-response repo-loaded)
(register-handler :get-repo-tree repo-handlers/get-repo-tree)
(register-handler :process-repo-tree-response repo-handlers/process-repo-tree-response repo-loaded)
