(ns reframe-git.components.languages-tree
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn main
  [languages]
  (reagent/create-class
    {:display-name "languages-tree"
     :component-did-mount
     #(println "component did mount")
     :reagent-render
     (fn [languages]
       [:div
        (keys @languages)])}))
