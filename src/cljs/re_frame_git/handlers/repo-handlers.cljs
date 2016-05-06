(ns re-frame-git.handlers.repo-handlers
  (:require [re-frame.core :as re-frame]
            [re-frame-git.db :as db]
            [re-frame-git.utils.core :refer [GET]]
            [clojure.string :refer [lower-case]]))

(defn set-repo-list
  [db [username]]
  (if (and
        (not (empty? (get-in db [:repo-list :items])))
        (= (get-in db [:repo-list :github-username]) username))
    db
    (do
      (re-frame/dispatch [:get-repo-list username])
      db)))

(defn get-repo-list
  [db [username]]
  (let [loading-flag-vector [:repo-list :loading]]
    (GET (str "/api/github/users/" username "/repos")
         #(re-frame/dispatch [:process-repo-list-response username %1])
         #(re-frame/dispatch [:api-error %1 loading-flag-vector])) ;]])) 
    (assoc-in db loading-flag-vector true)))

(defn process-repo-list-response
  [db [username response]]
  (update-in db [:repo-list] assoc :loading false :items response :github-username username))

(defn set-current-repo
  [db [username repo-name]]
  (let [repo-details (:repo-details db)
        repo-keyword (keyword (lower-case (str username "/" repo-name)))]
    (if (contains? repo-details repo-keyword)
      (do
        (println "in list")
        (let [item (repo-keyword repo-details)]
          (if (not-any? nil? (map #(get % 1) item))
            (do
              (re-frame/dispatch [:set-current-repo username repo-name])
              db)
            (assoc db :current-repo (repo-keyword repo-details)))))
      (do
        (re-frame/dispatch [:get-repo username repo-name])
        (println "getting")
        ;(update-in db merge [:repo-details] {repo-keyword {:loading true :details nil :tree nil}})
        (assoc db :current-repo {:loading true :details nil :tree nil :languages nil})))))

(defn get-repo
  [db [username repo-name]]
  (GET (str "/api/github/repos/" username "/" repo-name)
       #(re-frame/dispatch [:process-repo-response username repo-name %1])
       #(re-frame/dispatch [:api-error %1]))
  (re-frame/dispatch [:get-repo-branches username repo-name])
  db)

(defn process-repo-response
  [db [username repo-name response]]
  (assoc-in db [:repo-details (keyword (lower-case (str username "/" repo-name))) :details] response))

(defn get-repo-languages
  [db [username repo-name]]
  (println username)
  (GET (str "/api/github/repos/" username "/" repo-name "/languages")
       #(re-frame/dispatch [:process-repo-languages-response username repo-name %1])
       #(re-frame/dispatch [:api-error %1]))
  db)

(defn process-repo-languages-response
  [db [username repo-name response]]
  (assoc-in db [:repo-details (keyword (lower-case (str username "/" repo-name))) :languages] response))

(defn get-repo-branches
  [db [username repo-name]]
  (GET (str "/api/github/repos/" username "/" repo-name "/branches")
       #(re-frame/dispatch [:process-repo-branches-response username repo-name %1])
       #(re-frame/dispatch [:api-error %1]))
  db)

(defn process-repo-branches-response
  [db [username repo-name response]]
  (re-frame/dispatch
    [:get-repo-tree
     username
     repo-name
     (get-in (first (filter #(= (%1 :name) "master") response)) [:commit :sha])])
  (assoc-in db [:repo-details (keyword (lower-case (str username "/" repo-name))) :branches] response))

(defn get-repo-tree
  [db [username repo-name sha]]
  (GET (str "/api/github/repos/" username "/" repo-name "/git/trees/" sha "?recursive=1")
       #(re-frame/dispatch [:process-repo-tree-response username repo-name %1])
       #(re-frame/dispatch [:api-error %1]))
  db)

(defn process-repo-tree-response
  [db [username repo-name response]]
  (assoc-in db [:repo-details (keyword (lower-case (str username "/" repo-name))) :tree] response)
  (assoc-in db [:repo-details (keyword (lower-case (str username "/" repo-name))) :tree] response)
  (assoc-in db [:repo-details (keyword (lower-case (str username "/" repo-name))) :tree] response)
  (assoc-in db [:repo-details (keyword (lower-case (str username "/" repo-name))) :tree] response)
  (assoc-in db [:repo-details (keyword (lower-case (str username "/" repo-name))) :tree] response)
  (assoc-in db [:repo-details (keyword (lower-case (str username "/" repo-name))) :tree] response)
  (assoc-in db [:repo-details (keyword (lower-case (str username "/" repo-name))) :tree] response)
  (assoc-in db [:repo-details (keyword (lower-case (str username "/" repo-name))) :tree] response))
