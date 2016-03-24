(ns re-frame-git.handlers.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [re-frame.core :as re-frame]
            [re-frame-git.db :as db]
            [cljs.core.async :refer [<!]]
            [cljs-http.client :as client]
            [re-frame-git.handlers.repo-handlers :as repo-handlers]
            [re-frame-git.handlers.post-handlers :as post-handlers]))

(defn set-current-route
  [db [_ route]]
  (assoc-in db [:current-route] route))

(defn api-error
  [db [_ response]]
  (println "An API error has occured")
  (println (:error-text response))
  db)

(re-frame/register-handler
  :initialize-db
  (fn [_ _]
    db/default-db))
(re-frame/register-handler :set-current-route set-current-route)
(re-frame/register-handler :get-posts post-handlers/get-posts)
(re-frame/register-handler :get-repo-list repo-handlers/get-repo-list)
(re-frame/register-handler :process-repo-list-response repo-handlers/process-repo-list-response)
(re-frame/register-handler :process-posts-response post-handlers/process-posts-response)
(re-frame/register-handler :api-error api-error)
(re-frame/register-handler :get-repo repo-handlers/get-repo)
(re-frame/register-handler :process-repo-response repo-handlers/process-repo-response)
(re-frame/register-handler :get-repo-languages repo-handlers/get-repo-languages)
(re-frame/register-handler :process-repo-languages-response repo-handlers/process-repo-languages-response)
(re-frame/register-handler :get-repo-branches repo-handlers/get-repo-branches)
(re-frame/register-handler :process-repo-branches-response repo-handlers/process-repo-branches-response)
(re-frame/register-handler :get-repo-tree repo-handlers/get-repo-tree)
(re-frame/register-handler :process-repo-tree-response repo-handlers/process-repo-tree-response)
