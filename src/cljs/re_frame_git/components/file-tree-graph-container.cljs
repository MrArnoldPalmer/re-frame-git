(ns re-frame-git.components.file-tree-graph-container
  (:require [clojure.string :refer [split]]
            [re-frame-git.components.tree-map :refer [tree-map]]))

(defn format-item
  [item]
  (if (= (:type item) "blob")
    (let [path-vector (split (:path item) "/")]
      {:path (drop-last path-vector)
       :details {:name (last path-vector)
                 :size (:size item)}})
    (let [path-vector (split (:path item) "/")]
      {:path (drop-last path-vector)
       :details {:name (last path-vector)
                 :children []}})))

(defn get-indices
  [formatted-map item]
  (reduce (fn [index-vector path]
            (let [obj (first (filter #(= (:name %1) path) (get-in formatted-map index-vector)))]
              (if (nil? obj)
                index-vector
                (into index-vector [(.indexOf (to-array (get-in formatted-map index-vector)) obj) :children]))))
          [:children]
          (:path item)))

(defn format-file-tree-data
  [tree-graph-data]
  (reduce (fn [formatted-map item]
            (let [item (format-item item)]
              (update-in formatted-map
                         (get-indices formatted-map item)
                         conj (:details item))))
          {:name "root" :children []}
          (:tree tree-graph-data)))

(defn file-tree-graph-container
  [tree-data]
  [:div
   [tree-map (format-file-tree-data tree-data)]])
