(ns re-frame-git.repositories.views
  (:require [reagent.core :as reagent]
            [re-frame.core :refer [subscribe]]
            [re-frame-git.components.loading-indicator :refer [loading-indicator]]
            [re-frame-git.routes :as routes]))

(defn repo-list
  [repo-list]
  [:div
   (map (fn [repo]
         [:a
          {:href (routes/repo-details
                   {:github-username (get-in repo [:owner :login])
                    :repo-name (:name repo)})
           :key (:full_name repo)}
          [:h1
           {:key (:full_name repo)}
           (:name repo)]])
        repo-list)])

(defn main
  []
  (let [repo-list-state (subscribe [:repo-list])
        loading (:loading @repo-list-state)
        repo-list-items (:items @repo-list-state)]
    [:div
     [:h1 "Repositories"]
     (if loading
       [loading-indicator]
       [repo-list repo-list-items])]))
