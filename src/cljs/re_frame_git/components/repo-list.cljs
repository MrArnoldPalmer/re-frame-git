(ns re-frame-git.components.repo-list
  (:require [reagent.core :as reagent]))

(defn repo-list
  [repo-list]
  (map (fn [repo]
         [:h1 (:name repo)])
       repo-list))