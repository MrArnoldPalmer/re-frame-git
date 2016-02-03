(ns reframe-git.handlers
    (:require [re-frame.core :as re-frame]
              [reframe-git.db :as db]
              [ajax.core :as ajax]))

(defn build-api-url
  "Builds an api url using base github api url and endpoint path as argument"
  [endpoint]
  (str "https://api.github.com" endpoint))

(defn GET
  "Generic http GET function for handlers"
  [endpoint success-handler error-handler]
  (ajax/GET endpoint
            {:handler success-handler
             :error-handler error-handler
             :response-format :json
             :keywords? true}))

(re-frame/register-handler
  :initialize-db
  (fn [_ _]
    db/default-db))

(re-frame/register-handler
  :get-repo
  (fn [db [_ repo-name]]
    (GET (build-api-url (str "/repos/" repo-name))
         #(re-frame/dispatch [:process-repo-response repo-name %1])
         #(re-frame/dispatch [:process-repo-error repo-name %1]))
    db))

(re-frame/register-handler
  :process-repo-response
  (fn
    [db [_ repo-name response]]
    (re-frame/dispatch [:get-repo-languages repo-name])
    (assoc-in db [:repo-details] response)))

(re-frame/register-handler
  :process-repo-error
  (fn
    [db [_ [repo-name response]]]
    (println "Error getting repo information for " repo-name)
    (println response)
    db))

(re-frame/register-handler
  :get-repo-languages
  (fn
    [db [_ repo-name]]
    (GET (build-api-url (str "/repos/" repo-name "/languages"))
         #(re-frame/dispatch [:process-repo-languages-response repo-name %1])
         #(re-frame/dispatch [:process-repo-languages-error repo-name %1]))
    db))

(re-frame/register-handler
  :process-repo-languages-response
  (fn
    [db [_ repo-name response]]
    (assoc-in db [:repo-languages] response)))

(re-frame/register-handler
  :process-repo-languages-error
  (fn
    [db [_ [repo-name response]]]
    (println "Error getting repo language information for " repo-name)
    (println response)
    db))

(re-frame/register-handler
  :get-repo-tree
  (fn
    [db [_ repo-name sha]]
    (GET (build-api-url (str "/repos/" repo-name "/git/trees/" sha "?recursive=1"))
         #(re-frame/dispatch [:process-repo-tree-reponse repo-name %1])
         #(re-frame/dispatch [:process-repo-tree-error repo-name %1]))
    db))

(re-frame/register-handler
  :process-repo-tree-response
  (fn
    [db [_ repo-name response]]
    (assoc-in db [:repo-tree] response)))

(re-frame/register-handler
  :process-repo-tree-error
  (fn
    [db [_ repo-name response]]
    (println "Error getting repo tree for " repo-name)
    (println response)
    db))
