(ns reframe-git.components.file-tree-graph
   (:require [clojure.string :refer [split]]
             [re-frame.core :as re-frame]
             [reagent.core :as reagent]))

(defn get-file-name
  [path-strings]
  (let [final (last path-strings)]
    (if (boolean (re-find #"\." final))
      final
      nil)))

(defn format-tree-graph-data
  [tree-data]
  (reduce (fn [formatted-map item]
            (let [path-strings (split (:path item) "/")
                  file (get-file-name path-strings)]
              (println formatted-map)
              (if (and file (not (= (count path-strings) 1)))
                (do
                  (println path-strings)
                  (println file)
                  (assoc-in formatted-map (mapv keyword (into [:root] (drop-last path-strings))) file))
                formatted-map)))
          {}
          (:tree tree-data)))

(defn main
  [tree-graph-data]
  (reagent/create-class
    {:display-name "file-tree"
     :component-did-mount
     (fn []
       (println (format-tree-graph-data tree-graph-data))
       (println "graph component did mount"))
     :reagent-render
     (fn [tree-graph-data]
       [:div
        (keys tree-graph-data)])}))

