(ns re-frame-git.repo-details.handlers
  (:require [re-frame.core :as re-frame]
            [re-frame-git.db :as db]
            [re-frame-git.handlers :as handlers]
            [re-frame-git.middleware :refer [repo-loaded]]
            [re-frame-git.utils.core :as utils]
            [clojure.string :as string]))

(defn set-current-repo
  [db [username repo-name]]
  (let [repo-details (:repo-details db)
        repo-keyword (utils/build-repo-keyword username repo-name)]
    (if (and
          (contains? repo-details repo-keyword)
          (utils/item-loaded (repo-keyword repo-details)))
      (assoc-in db [:current-repo] (assoc (repo-keyword repo-details) :loading false))
      (do
        (re-frame/dispatch [:load-repo-details username repo-name])
        (-> db
            (assoc-in
              [:repo-details repo-keyword] {:tree nil :details nil :branches nil :readme nil})
            (assoc-in
              [:current-repo] {:loading true :tree nil :details nil :branches nil}))))))

(defn load-repo-details
  [db [username repo-name]]
  (re-frame/dispatch [:get-repo username repo-name])
  (re-frame/dispatch [:get-repo-readme username repo-name])
  (re-frame/dispatch [:get-repo-branches username repo-name])
  db)

(defn get-repo
  [db [username repo-name]]
  (utils/GET (str "/api/github/repos/" username "/" repo-name)
       #(re-frame/dispatch [:process-repo-response username repo-name %1])
       #(re-frame/dispatch [:api-error %1]))
  db)

(defn process-repo-response
  [db [username repo-name response]]
  (assoc-in db [:repo-details (utils/build-repo-keyword username repo-name) :details] response))

(defn get-repo-readme
  [db [username repo-name]]
  (utils/GET (str "/api/github/repos/" username "/" repo-name "/readme")
       {:headers {"accept" "application/vnd.github.VERSION.raw"}}
       #(re-frame/dispatch [:process-repo-readme-response username repo-name %1])
       #(re-frame/dispatch [:api-error %1]))
  db)

(defn process-repo-readme-response
  [db [username repo-name response]]
  (assoc-in db [:repo-details (utils/build-repo-keyword username repo-name) :readme] response))

(defn get-repo-languages
  [db [username repo-name]]
  (utils/GET (str "/api/github/repos/" username "/" repo-name "/languages")
       #(re-frame/dispatch [:process-repo-languages-response username repo-name %1])
       #(re-frame/dispatch [:api-error %1]))
  db)

(defn process-repo-languages-response
  [db [username repo-name response]]
  (assoc-in db [:repo-details (utils/build-repo-keyword username repo-name) :languages] response))

(defn get-repo-branches
  [db [username repo-name]]
  (utils/GET (str "/api/github/repos/" username "/" repo-name "/branches")
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
  (assoc-in db [:repo-details (utils/build-repo-keyword username repo-name) :branches] response))

(defn get-repo-tree
  [db [username repo-name sha]]
  (utils/GET (str "/api/github/repos/" username "/" repo-name "/git/trees/" sha "?recursive=1")
   #(re-frame/dispatch [:process-repo-tree-response username repo-name %1])
   #(re-frame/dispatch [:api-error %1]))
  db)

(defn process-repo-tree-response
  [db [username repo-name response]]
  (assoc-in db [:repo-details (utils/build-repo-keyword username repo-name) :tree] response))

(handlers/register-handler :load-repo-details load-repo-details)
(handlers/register-handler :set-current-repo set-current-repo)
(handlers/register-handler :get-repo get-repo)
(handlers/register-handler :process-repo-response process-repo-response repo-loaded)
(handlers/register-handler :get-repo-readme get-repo-readme)
(handlers/register-handler :process-repo-readme-response process-repo-readme-response repo-loaded)
(handlers/register-handler :get-repo-languages get-repo-languages)
(handlers/register-handler :process-repo-languages-response process-repo-languages-response repo-loaded)
(handlers/register-handler :get-repo-branches get-repo-branches)
(handlers/register-handler :process-repo-branches-response process-repo-branches-response repo-loaded)
(handlers/register-handler :get-repo-tree get-repo-tree)
(handlers/register-handler :process-repo-tree-response process-repo-tree-response repo-loaded)
