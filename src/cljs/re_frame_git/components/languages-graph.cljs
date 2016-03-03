(ns re-frame-git.components.languages-graph
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn main
  [languages]
  (reagent/create-class
    {:display-name "languages-graph"
     :component-did-mount
     #(println "component did mount")
     :reagent-render
     (fn [languages]
       [:div
        (keys @languages)])}))
