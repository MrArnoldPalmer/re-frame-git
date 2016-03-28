(ns re-frame-git.containers.repositories
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [re-frame-git.components.repo-list :refer [repo-list]]))

(defn repositories
  []
  (let [repo-list-data (re-frame/subscribe [:repo-list])]
    [:div
     [:h1 "Repositories"]
     [repo-list @repo-list-data]]))
