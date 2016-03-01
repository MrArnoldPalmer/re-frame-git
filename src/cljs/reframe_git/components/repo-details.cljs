(ns reframe-git.components.repo-details
    (:require [re-frame.core :as re-frame]
              [reframe-git.components.get-repo :as get-repo]
              [reframe-git.components.languages :as languages]
              [reframe-git.components.file-tree :as file-tree]))

(defn repo-details []
  (let [repo (re-frame/subscribe [:repo-details])]
    [:div
     (:full_name @repo)
     [get-repo/main]
     [file-tree/main]]))
