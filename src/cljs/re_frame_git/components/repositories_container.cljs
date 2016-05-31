(ns re-frame-git.components.repositories-container
  (:require [reagent.core :as reagent]
            [re-frame.core :refer [subscribe]]
            [re-frame-git.components.loading-indicator :refer [loading-indicator]]
            [re-frame-git.components.repo-list :refer [repo-list]]))

(defn repositories-container
  []
  (let [repo-list-state (subscribe [:repo-list])
        loading (:loading @repo-list-state)
        repo-list-items (:items @repo-list-state)]
    [:div
     [:h1 "Repositories"]
     (if loading
       [loading-indicator]
       [repo-list repo-list-items])]))
