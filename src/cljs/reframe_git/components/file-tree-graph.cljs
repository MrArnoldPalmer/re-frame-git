(ns reframe-git.components.file-tree-graph
   (:require [clojure.string :refer [split]]
             [re-frame.core :as re-frame]
             [reagent.core :as reagent]))

(defn format-tree-graph-data
  [tree-data]
  (->> (:tree tree-data)
       (map :path)
       (map #(split % "/"))
       (map (fn [item] (map keyword item)))
       (println)))

(defn main
  [tree-graph-data]
  (reagent/create-class
    {:display-name "file-tree"
     :component-did-mount
     (fn []
       (format-tree-graph-data tree-graph-data)
       (println "graph component did mount"))
     :reagent-render
     (fn [tree-graph-data]
       [:div
        (keys tree-graph-data)])}))

