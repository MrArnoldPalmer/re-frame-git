(ns reframe-git.components.file-tree-graph
   (:require [clojure.string :refer [split]]
             [re-frame.core :as re-frame]
             [reagent.core :as reagent]))

(defn build-file-map
  [file-name info]
  {:name file-name
   :size (:size info)})

(defn format-item
  [item]
  (let [paths (split (:path item) "/")]
    (if (->> paths
             (last)
             (re-find #"\.")
             (boolean))
      {:location (->> paths
                      (drop-last)
                      (map keyword)
                      (into [:root]))
       :type "file"
       :details (build-file-map (last paths) item)}
      {:location (->> paths
                      (map keyword)
                      (into [:root]))
       :type "directory"})))

(defn format-tree-graph-data
  [tree-data]
  (reduce (fn [formatted-map item]
            (let [formatted-item (format-item item)]
              (println formatted-item)
              (println formatted-map)
              formatted-map))
          {:files []
           :directories []}
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

