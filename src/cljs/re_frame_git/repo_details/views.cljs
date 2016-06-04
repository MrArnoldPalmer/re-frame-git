(ns re-frame-git.repo-details.views
  (:require [re-frame.core :refer [subscribe]]
            [re-frame-git.components.loading-indicator :refer [loading-indicator]]
            [re-frame-git.components.file-tree-graph-container :refer [file-tree-graph-container]]
            [re-frame-git.components.readme :refer [readme]]))

(defn main []
  (let [current-repo (subscribe [:current-repo])
        is-loading (:loading @current-repo)
        repo-tree (:tree @current-repo)
        repo-details (:details @current-repo)
        branches (:branches @current-repo)
        readme-mardown (:readme @current-repo)]
    (if is-loading
      [loading-indicator]
      [:div
       (:full_name repo-details)
       [readme readme-mardown]
       [file-tree-graph-container repo-tree]])))
