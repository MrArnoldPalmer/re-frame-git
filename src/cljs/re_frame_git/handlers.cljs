(ns re-frame-git.handlers
    (:require [re-frame.core :as re-frame]
              [re-frame-git.db :as db]
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

(defn get-repo
  [db [_ repo-name]]
  (GET (build-api-url (str "/repos/" repo-name))
       #(re-frame/dispatch [:process-repo-response repo-name %1])
       #(re-frame/dispatch [:process-repo-error repo-name %1]))
  db)

(defn process-repo-response
  [db [_ repo-name response]]
  (re-frame/dispatch [:get-repo-languages repo-name])
  (re-frame/dispatch [:get-repo-branches repo-name])
  (assoc-in db [:repo-details] response))

(defn process-repo-error
  [db [_ repo-name response]]
  (println "Error getting repo information for " repo-name)
  (println response)
  db)

(defn get-repo-languages
  [db [_ repo-name]]
  (GET (build-api-url (str "/repos/" repo-name "/languages"))
       #(re-frame/dispatch [:process-repo-languages-response repo-name %1])
       #(re-frame/dispatch [:process-repo-languages-error repo-name %1]))
  db)

(defn process-repo-languages-response
  [db [_ repo-name response]]
  (assoc-in db [:repo-languages] response))

(defn process-repo-languages-error
  [db [_ repo-name response]]
  (println "Error getting repo language information for " repo-name)
  (println response)
  db)

(defn get-repo-branches
  [db [_ repo-name]]
  (GET (build-api-url (str "/repos/" repo-name "/branches"))
       #(re-frame/dispatch [:process-repo-branches-response repo-name %1])
       #(re-frame/dispatch [:process-repo-branches-error repo-name %1]))
  db)

(defn process-repo-branches-response
  [db [_ repo-name response]]
  (re-frame/dispatch
    [:get-repo-tree
     repo-name
     (get-in (first (filter #(= (%1 :name) "master") response)) [:commit :sha])])
  (assoc-in db [:repo-branches] response))

(defn process-repo-branches-error
  [db [_ repo-name response]]
  (println "Error getting repo branch information for " repo-name)
  (println response))

(defn get-repo-tree
  [db [_ repo-name sha]]
  (GET (build-api-url (str "/repos/" repo-name "/git/trees/" sha "?recursive=1"))
       #(re-frame/dispatch [:process-repo-tree-response repo-name %1])
       #(re-frame/dispatch [:process-repo-tree-error repo-name %1]))
  db)

(defn process-repo-tree-response
  [db [_ repo-name response]]
  (assoc-in db [:repo-tree] response))

(defn process-repo-tree-error
  [db [_ repo-name response]]
  (println "Error getting repo tree for " repo-name)
  (println response)
  db)

(re-frame/register-handler :get-repo get-repo)
(re-frame/register-handler :process-repo-response process-repo-response)
(re-frame/register-handler :process-repo-error process-repo-error)
(re-frame/register-handler :get-repo-languages get-repo-languages)
(re-frame/register-handler :process-repo-languages-response process-repo-languages-response)
(re-frame/register-handler :process-repo-languages-error process-repo-languages-error)
(re-frame/register-handler :get-repo-branches get-repo-branches)
(re-frame/register-handler :process-repo-branches-response process-repo-branches-response)
(re-frame/register-handler :process-repo-branches-error process-repo-branches-error)
(re-frame/register-handler :get-repo-tree get-repo-tree)
(re-frame/register-handler :process-repo-tree-response process-repo-tree-response)
(re-frame/register-handler :process-repo-tree-error process-repo-tree-error)
