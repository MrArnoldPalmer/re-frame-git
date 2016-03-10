(ns re-frame-git.components.file-tree-graph
   (:require [clojure.string :refer [split]]
             [re-frame.core :as re-frame]
             [reagent.core :as reagent]
             [cljsjs.d3]))


(defn position
  []
  (this-as this
           (-> this
               (.style "left"
                       (fn [d]
                         (str (.-x d) "px")))
               (.style "top"
                       (fn [d]
                         (str (.-y d) "px")))
               (.style "width"
                       (fn [d]
                         (str (max 0 (- (.-dx d) 1)) "px")))
               (.style "height"
                       (fn [d]
                         (str (max 0 (- (.-dy d) 1)) "px"))))))

(defn compute-font-size
  [d]
  (str
    (max 20 (.sqrt js/Math (.-area d)))
    "px"))

(defn render-tree-map
  [tree-map-data-map]
  (let [width (- (.-innerWidth js/window) 40)
        height (- (.-innerHeight js/window) 40)
        color (-> js/d3
                  (.-scale)
                  (.category20c))
        div (-> js/d3
                (.select "#file-tree-graph")
                (.style "position" "relative"))
        tree-map (-> js/d3
                     (.-layout)
                     (.treemap)
                     (.size (clj->js [width height]))
                     (.value (fn [d]
                               (.-size d))))
        node (-> div
                 (.datum (clj->js tree-map-data-map))
                 (.selectAll ".node")
                 (.data (.-nodes tree-map))
                 (.enter)
                 (.append "div")
                 (.attr "class" "node")
                 (.call position)
                 (.style "background-color"
                         (fn [d]
                           (if (= (.-name d) "tree")
                             "#fff"
                             (color (.-name d)))))
                 (.append "div")
                 (.style "font-size" compute-font-size))]))

(def test-data
  {:name "test"
   :children [{:name "again" :size 100} {:name "again2" :size 500} {:name "nested" :children [{:name "nested file" :size 350}]}]})

(defn format-file-map
  [item]
  (let [file-structure (split (:path item) "/")
        file (last file-structure)
        path (into ["root"] (drop-last file-structure))]
    {:location path
     :name file
     :size (:size item)}))

(defn deep-merge [formatted-map item-map]
  (merge-with (fn [x y]
                (println x y)
                (cond (vector? y) (into x y) 
                      (map? y) (deep-merge x y)
                      :else y)) 
                 formatted-map item-map))

(defn merge-directory
  [formatted-map item]
  (let [path-vector (:location item)
        nested-item (reduce (fn [formatted-item path]
                              (if (empty? formatted-item)
                                [{:name (:name item) :size (:size item)}]
                                {:name path :children formatted-item}))
                            []
                            path-vector)]
    (deep-merge formatted-map {:name "root" :children nested-item})))

(defn format-tree-map-data
  [tree-graph-data]
  (->> tree-graph-data
       (:tree)
       (filter #(= (:type %1) "blob"))
       (map format-file-map)
       ;(sort-by #(count (:location %1)))
       (reduce (fn [formatted-map item]
                 (merge-directory formatted-map item))
               {:name "root" :children[]})))
(defn main
  [tree-graph-data]
  (reagent/create-class
    {:display-name "file-tree"
     :component-did-mount
     (fn []
       (println "mount")
       (let [tree-map-data (format-tree-map-data tree-graph-data)]
         (println tree-map-data)
         (render-tree-map tree-map-data)))
     :reagent-render
     (fn [tree-graph-data]
       [:div#file-tree-graph])}))

