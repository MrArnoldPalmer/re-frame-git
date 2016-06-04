(ns re-frame-git.repositories.handlers
  (:require [re-frame.core :as re-frame]
            [re-frame-git.db :as db]
            [re-frame-git.utils.core :as utils]
            [clojure.string :as string]))

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
    (utils/GET (str "/api/github/users/" username "/repos")
         #(re-frame/dispatch [:process-repo-list-response username %1])
         #(re-frame/dispatch [:api-error %1 loading-flag-vector])) ;]])) 
    (assoc-in db loading-flag-vector true)))

(defn process-repo-list-response
  [db [username response]]
  (update-in db [:repo-list] assoc :loading false :items response :github-username username))
