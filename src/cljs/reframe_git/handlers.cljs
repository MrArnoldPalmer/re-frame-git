(ns reframe-git.handlers
    (:require [re-frame.core :as re-frame]
              [reframe-git.db :as db]
              [ajax.core :as ajax]))

(defn build-api-url
  "Builds an api url using base github api url and endpoint path as argument"
  [endpoint]
  (str "https://api.github.com" endpoint))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/register-handler
  :get-repo
  (fn [db [_ repo-name]]
    (ajax/GET (build-api-url (str "/repos/" repo-name))
              {:handler #(re-frame/dispatch [:process-repo-response repo-name %1])
               :error-handler #(re-frame/dispatch [:process-repo-error repo-name %1])
               :response-format :json
               :keywords? true})
    db))

(re-frame/register-handler
  :process-repo-response
  (fn
    [db [_ repo-name response]]
    (assoc-in db [:repo-details] response)))

(re-frame/register-handler
  :process-repo-error
  (fn
    [db [_ [repo-name response]]]
    (println "Error getting repo information for " repo-name)
    (println response)
    db))

