(ns re-frame-git.components.repo-details-container
    (:require [re-frame.core :as re-frame]
              [re-frame-git.components.get-repo :as get-repo]
              [re-frame-git.components.languages :as languages]
              [re-frame-git.components.file-tree-graph-container :refer [file-tree-graph-container]]))

(defn repo-details-container []
  (let [repo (re-frame/subscribe [:repo-details])]
    [:div
     (:full_name @repo)
     [file-tree-graph-container]]))
