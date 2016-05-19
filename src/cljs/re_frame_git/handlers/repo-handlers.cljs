(ns re-frame-git.handlers.repo-handlers
  (:require [re-frame.core :refer [dispatch]]
            [re-frame-git.db :as db]
            [re-frame-git.utils.core :refer [GET item-loaded build-repo-keyword]]
            [clojure.string :refer [lower-case]]))

(defn set-repo-list
  [db [username]]
  (if (and
        (not (empty? (get-in db [:repo-list :items])))
        (= (get-in db [:repo-list :github-username]) username))
    db
    (do
      (dispatch [:get-repo-list username])
      db)))

(defn get-repo-list
  [db [username]]
  (let [loading-flag-vector [:repo-list :loading]]
    (GET (str "/api/github/users/" username "/repos")
         #(dispatch [:process-repo-list-response username %1])
         #(dispatch [:api-error %1 loading-flag-vector])) ;]])) 
    (assoc-in db loading-flag-vector true)))

(defn process-repo-list-response
  [db [username response]]
  (update-in db [:repo-list] assoc :loading false :items response :github-username username))

(defn set-current-repo
  [db [username repo-name]]
  (let [repo-details (:repo-details db)
        repo-keyword (build-repo-keyword username repo-name)]
    (if (and
          (contains? repo-details repo-keyword)
          (item-loaded (repo-keyword repo-details)))
      (assoc-in db [:current-repo] (assoc (repo-keyword repo-details) :loading false))
      (do
        (dispatch [:load-repo-details username repo-name])
        (-> db
            (assoc-in
              [:repo-details repo-keyword] {:tree nil :details nil :branches nil :readme nil})
            (assoc-in
              [:current-repo] {:loading true :tree nil :details nil :branches nil}))))))

(defn load-repo-details
  [db [username repo-name]]
  (dispatch [:get-repo username repo-name])
  (dispatch [:get-repo-readme username repo-name])
  (dispatch [:get-repo-branches username repo-name])
  db)

(defn get-repo
  [db [username repo-name]]
  (GET (str "/api/github/repos/" username "/" repo-name)
       #(dispatch [:process-repo-response username repo-name %1])
       #(dispatch [:api-error %1]))
  db)

(defn process-repo-response
  [db [username repo-name response]]
  (assoc-in db [:repo-details (build-repo-keyword username repo-name) :details] response))

(defn get-repo-readme
  [db [username repo-name]]
  (GET (str "/api/github/repos/" username "/" repo-name "/readme?media-type=application/vnd.github.VERSION.raw")
       #(dispatch [:process-repo-readme-response username repo-name %1])
       #(dispatch [:api-error %1]))
  db)

(defn process-repo-readme-response
  [db [username repo-name response]]
  (assoc-in db [:repo-details (build-repo-keyword username repo-name) :readme] response))

(defn get-repo-languages
  [db [username repo-name]]
  (GET (str "/api/github/repos/" username "/" repo-name "/languages")
       #(dispatch [:process-repo-languages-response username repo-name %1])
       #(dispatch [:api-error %1]))
  db)

(defn process-repo-languages-response
  [db [username repo-name response]]
  (assoc-in db [:repo-details (build-repo-keyword username repo-name) :languages] response))

(defn get-repo-branches
  [db [username repo-name]]
  (GET (str "/api/github/repos/" username "/" repo-name "/branches")
       #(dispatch [:process-repo-branches-response username repo-name %1])
       #(dispatch [:api-error %1]))
  db)

(defn process-repo-branches-response
  [db [username repo-name response]]
  (dispatch
    [:get-repo-tree
     username
     repo-name
     (get-in (first (filter #(= (%1 :name) "master") response)) [:commit :sha])])
  (assoc-in db [:repo-details (build-repo-keyword username repo-name) :branches] response))

(defn get-repo-tree
  [db [username repo-name sha]]
  (GET (str "/api/github/repos/" username "/" repo-name "/git/trees/" sha "?recursive=1")
       #(dispatch [:process-repo-tree-response username repo-name %1])
       #(dispatch [:api-error %1]))
  db)

(defn process-repo-tree-response
  [db [username repo-name response]]
  (assoc-in db [:repo-details (build-repo-keyword username repo-name) :tree] response))
