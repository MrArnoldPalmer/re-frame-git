(ns re-frame-git.handlers
  (:require [re-frame.core :as re-frame]
            [re-frame-git.db :as db]
            [re-frame-git.config :as config]))

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
