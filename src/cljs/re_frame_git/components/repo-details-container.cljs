(ns re-frame-git.components.repo-details-container
    (:require [re-frame.core :refer [subscribe]]
              [re-com.core :refer [md-icon-button]]
              [re-frame-git.components.loading-indicator :refer [loading-indicator]]
              [re-frame-git.components.file-tree-graph-container :refer [file-tree-graph-container]]))

(defn repo-details-container []
  (let [current-repo (subscribe [:current-repo])
        is-loading (:loading @current-repo)
        repo-tree (:tree @current-repo)
        repo-details (:details @current-repo)
        branches (:branches @current-repo)]
    (if is-loading
      [loading-indicator]
      [:div
       (:full_name repo-details)
       [md-icon-button
        :md-icon-name "zmdi-github"
        :size :larger
        :class "repoDetailsIcon"]
       [file-tree-graph-container repo-tree]])))
