(ns reframe-git.components.file-tree-graph
   (:require [re-frame.core :as re-frame]
             [reagent.core :as reagent]))

(defn main
  [tree-graph-data]
  (reagent/create-class
    {:display-name "file-tree"
     :component-did-mount
     (fn []
       (println (tree-graph-data :tree))
       (println "graph component did mount"))
     :reagent-render
     (fn [tree-graph-data]
       [:div
        (keys tree-graph-data)])}))

