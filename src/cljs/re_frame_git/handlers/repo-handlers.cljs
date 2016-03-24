(ns re-frame-git.handlers.repo-handlers
  (:require [re-frame.core :as re-frame]
            [re-frame-git.db :as db]
            [re-frame-git.helpers :refer [GET]]))

(defn get-repo-list
  [db [_ username]]
  (GET (str "/api/github/repositories/" username)
       #(re-frame/dispatch [:process-repo-list-response %1])
       #(re-frame/dispatch [:api-error %1]))
  (assoc-in db [:github-username] username))

(defn process-repo-list-response
  [db [_ response]]
  (assoc-in db [:repo-list] response))

(defn get-repo
  [db [_ username repo-name]]
  (GET (str "/api/github/repository/" username "/" repo-name)
       #(re-frame/dispatch [:process-repo-response repo-name %1])
       #(re-frame/dispatch [:api-error %1]))
  db)

(defn process-repo-response
  [db [_ repo-name response]]
  (re-frame/dispatch [:get-repo-languages repo-name])
  (re-frame/dispatch [:get-repo-branches repo-name])
  (assoc-in db [:repo-details] response))

(defn get-repo-languages
  [db [_ repo-name]]
  (GET (str "/repos/" repo-name "/languages")
       #(re-frame/dispatch [:process-repo-languages-response repo-name %1])
       #(re-frame/dispatch [:api-error %1]))
  db)

(defn process-repo-languages-response
  [db [_ repo-name response]]
  (assoc-in db [:repo-languages] response))

(defn get-repo-branches
  [db [_ repo-name]]
  (GET (str "/repos/" repo-name "/branches")
       #(re-frame/dispatch [:process-repo-branches-response repo-name %1])
       #(re-frame/dispatch [:api-error %1]))
  db)

(defn process-repo-branches-response
  [db [_ repo-name response]]
  (re-frame/dispatch
    [:get-repo-tree
     repo-name
     (get-in (first (filter #(= (%1 :name) "master") response)) [:commit :sha])])
  (assoc-in db [:repo-branches] response))

(defn get-repo-tree
  [db [_ repo-name sha]]
  (GET (str "/repos/" repo-name "/git/trees/" sha "?recursive=1")
       #(re-frame/dispatch [:process-repo-tree-response repo-name %1])
       #(re-frame/dispatch [:api-error %1]))
  db)

(defn process-repo-tree-response
  [db [_ repo-name response]]
  (assoc-in db [:repo-tree] response))
