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

(defn main
  [tree-graph-data]
  (reagent/create-class
    {:display-name "file-tree"
     :component-did-mount
     (fn []
       (println "mount")
       (let [tree-map-data (format-tree-map-data tree-graph-data)]
         (println tree-map-data)
         (render-tree-map test-data)))
     :reagent-render
     (fn [tree-graph-data]
       [:div#file-tree-graph])}))

