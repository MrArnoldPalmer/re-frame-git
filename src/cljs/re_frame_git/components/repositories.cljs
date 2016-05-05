(ns re-frame-git.components.repositories
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [re-frame-git.components.loading-indicator :refer [loading-indicator]]
            [re-frame-git.components.repo-list :refer [repo-list]]))

(defn repositories
  []
  (let [repo-list-state (re-frame/subscribe [:repo-list])
        loading (:loading @repo-list-state)
        repo-list-items (:items @repo-list-state)]
    [:div
     [:h1 "Repositories"]
     (if loading
       [loading-indicator]
       [repo-list repo-list-items])]))
