(ns re-frame-git.components.repo-list
  (:require [reagent.core :as reagent]))

(defn repo-list
  [repo-list]
  [:div
   (map (fn [repo]
         [:h1
          {:key (:full_name repo)}
          (:name repo)])
       repo-list)])
