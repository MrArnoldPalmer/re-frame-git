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

(defn format-item
  [item]
  (if (= (:type item) "blob")
    (let [path-vector (split (:path item) "/")
          path (drop-last path-vector)
          file (last path-vector)]
      {:type "file"
       :path path
       :name file
       :size (:size item)})
    (let [path-vector (split (:path item) "/")
          path (drop-last path-vector)
          name (last path-vector)]
      {:type "tree"
       :path path
       :name name})))

(defn index-of
  [item items]
  (.indexOf (to-array items) item))

(defn deep-merge
  [formatted-map item]
  (let [indices (reduce (fn [index-vector path]
                          (let [obj (first (filter #(= (:name %1) path) (get-in formatted-map index-vector)))]
                            (if (nil? obj)
                              index-vector
                              (into index-vector [(index-of obj (get-in formatted-map index-vector)) :children]))))
                        [:children]
                        (:path item))]
    (if (= (:type item) "tree")
      (update-in formatted-map indices conj {:name (:name item) :children []})
      (update-in formatted-map indices conj {:name (:name item) :size (:size item)}))))

(defn format-file-tree-data
  [tree-graph-data]
  (reduce (fn [formatted-map item]
            (let [item (format-item item)]
              (deep-merge formatted-map item)))
          {:name "root" :children []}
          (:tree tree-graph-data)))

(defn main
  [tree-graph-data]
  (reagent/create-class
    {:display-name "file-tree"
     :component-did-mount
     (fn []
       (render-tree-map (format-file-tree-data tree-graph-data)))
     :reagent-render
     (fn [tree-graph-data]
       [:div#file-tree-graph])}))

