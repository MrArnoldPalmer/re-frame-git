(ns re-frame-git.components.file-tree-graph
   (:require [clojure.string :refer [split]]
             [re-frame.core :as re-frame]
             [reagent.core :as reagent]
             [cljsjs.d3]))

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

(defn format-tree-map-data
  [tree-data]
  (reduce (fn [formatted-map item]
            (let [formatted-item (format-item item)]
              (if (= (:type formatted-item) "directory")
                (assoc-in formatted-map (:location formatted-item) {:children []})
                (let [file (:details formatted-item)
                      directory (conj (:location formatted-item) :children)]
                  (assoc-in formatted-map directory (conj (get-in formatted-map directory) file))))))
          {}
          (:tree tree-data)))

(defn build-tree-map
  []
  (-> js/d3
      (.-layout)
      (.treemap)
      (.size [500 500])
      (.sticky true)
      (.value (fn [d] (.-size d)))))

(defn position
  [this]
  (println this)
  (-> this
      (.style "left" (fn [d] (str (.-x d) "px")))
      (.style "top" (fn [d] (str (.-x d) "px")))
      (.style "width" (fn [d] (str (max 0, (- (.-dx d) 1)) "px")))
      (.style "height" (fn [d] (str (max 0, (- (.-dy d) 1)) "px")))))

(defn mount-tree-map
  [tree-map element data]
  (let [div (-> js/d3
                (.select element)
                (.append "div")
                (.style "position" "relative")
                (.style "width" "500px")
                (.style "height" "500px"))
        node (-> div
                 (.datum data)
                 (.selectAll ".node")
                 (.data (.-nodes tree-map))
                 (.enter)
                 (.append "div")
                 (.attr "class" "node")
                 (.style "background" "blue")
                 (.style "position" "absolute")
                 (.style "width" "100%")
                 (.style "height" "100%")
                 (.text (fn [d] (.-name d))))]
    (println div)
    (println node)))

(defn main
  [tree-graph-data]
  (reagent/create-class
    {:display-name "file-tree"
     :component-did-update
     (fn [tree-graph-data]
       (println "update")
       (let [tree-map (build-tree-map)]
         (mount-tree-map tree-map "#file-tree-graph" (format-tree-map-data tree-graph-data))))
     :component-did-mount
     (fn []
       (let [tree-map (build-tree-map)]
         (mount-tree-map tree-map "#file-tree-graph" (format-tree-map-data tree-graph-data))))
     :reagent-render
     (fn [tree-graph-data]
       [:div#file-tree-graph])}))

