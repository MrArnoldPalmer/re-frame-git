(ns re-frame-git.components.repo-list
  (:require [reagent.core :as reagent]
            [re-frame-git.routes :refer [repo-details-route]]))

(defn repo-list
  [repo-list]
  [:div
   (map (fn [repo]
         [:a
          {:href (repo-details-route
                   {:github-username (get-in repo [:owner :login])
                    :repo-name (:name repo)})}
          [:h1
           {:key (:full_name repo)}
           (:name repo)]])
       repo-list
       repo-list)])
