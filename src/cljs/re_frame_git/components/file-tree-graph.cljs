(ns re-frame-git.components.file-tree-graph
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

(defn format-tree-map
  [tree-data]
  (reduce (fn [formatted-map item]
            (let [formatted-item (format-item item)]
              (if (= (:type formatted-item) "directory")
                (assoc-in formatted-map (:location formatted-item) {:files []})
                (let [file (:details formatted-item)
                      directory (conj (:location formatted-item) :files)]
                  (assoc-in formatted-map directory (conj (get-in formatted-map directory) file))))))
          {}
          (:tree tree-data)))

(defn main
  [tree-graph-data]
  (reagent/create-class
    {:display-name "file-tree"
     :component-did-mount
     (fn []
       (-> tree-graph-data
           format-tree-map
           println))
     :reagent-render
     (fn [tree-graph-data]
       [:div
        (keys tree-graph-data)])}))

