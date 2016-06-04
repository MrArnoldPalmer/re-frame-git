(ns re-frame-git.components.repo-list
  (:require [reagent.core :as reagent]
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
