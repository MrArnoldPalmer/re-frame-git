(ns reframe-git.components.repo-details
    (:require [re-frame.core :as re-frame]
              [reframe-git.components.get-repo :as get-repo]))

(defn main []
  (let [repo (re-frame/subscribe [:repo-details])]
    [:div
     (:full_name @repo)
     [get-repo/main]]))
