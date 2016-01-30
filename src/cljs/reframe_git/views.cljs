(ns reframe-git.views
    (:require [re-frame.core :as re-frame]
              [reframe-git.components.repo-details :as repo-details]))

(defn application []
  (fn []
    [:div
     [repo-details/main]]))
