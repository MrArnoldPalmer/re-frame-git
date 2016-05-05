(ns re-frame-git.handlers.repo-handlers
  (:require [re-frame.core :as re-frame]
            [re-frame-git.db :as db]
            [re-frame-git.utils.core :refer [GET]]))

(defn get-repo-list
  [db [_ username]]
  (let [loading-flag-vector [:repo-list :loading]]
    (GET (str "/api/github/users/" username "/repos")
         #(re-frame/dispatch [:process-repo-list-response username %1])
         #(re-frame/dispatch [:api-error %1 loading-flag-vector])) ;]])) 
    (assoc-in db loading-flag-vector true)))

(defn process-repo-list-response
  [db [_ username response]]
  (update-in db [:repo-list] assoc :loading false :items response :github-username username))

(defn get-repo
  [db [_ username repo-name]]
  (GET (str "/api/github/repos/" username "/" repo-name)
       #(re-frame/dispatch [:process-repo-response username repo-name %1])
       #(re-frame/dispatch [:api-error %1]))
  db)

(defn process-repo-response
  [db [_ username repo-name response]]
  (re-frame/dispatch [:get-repo-branches username repo-name])
  (assoc-in db [:repo-details] response))

(defn get-repo-languages
  [db [_ username repo-name]]
  (println username)
  (GET (str "/api/github/repos/" username "/" repo-name "/languages")
       #(re-frame/dispatch [:process-repo-languages-response repo-name %1])
       #(re-frame/dispatch [:api-error %1]))
  db)

(defn process-repo-languages-response
  [db [_ repo-name response]]
  (assoc-in db [:repo-languages] response))

(defn get-repo-branches
  [db [_ username repo-name]]
  (GET (str "/api/github/repos/" username "/" repo-name "/branches")
       #(re-frame/dispatch [:process-repo-branches-response username repo-name %1])
       #(re-frame/dispatch [:api-error %1]))
  db)

(defn process-repo-branches-response
  [db [_ username repo-name response]]
  (re-frame/dispatch
    [:get-repo-tree
     username
     repo-name
     (get-in (first (filter #(= (%1 :name) "master") response)) [:commit :sha])])
  (assoc-in db [:repo-branches] response))

(defn get-repo-tree
  [db [_ username repo-name sha]]
  (GET (str "/api/github/repos/" username "/" repo-name "/git/trees/" sha "?recursive=1")
       #(re-frame/dispatch [:process-repo-tree-response username repo-name %1])
       #(re-frame/dispatch [:api-error %1]))
  db)

(defn process-repo-tree-response
  [db [_ username repo-name response]]
  (assoc-in db [:repo-tree] response))
