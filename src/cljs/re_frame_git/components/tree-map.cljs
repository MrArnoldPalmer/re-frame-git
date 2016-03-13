(ns re-frame-git.components.tree-map
   (:require [re-frame.core :as re-frame]
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

(defn remove-tree-map
  [id]
  (-> js/d3
      (.select id)
      (.remove)))

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

(defn tree-map
  [tree-graph-data]
  (reagent/create-class
    {:display-name "file-tree"
     :component-did-update
     (fn []
       (println "did update")
       (remove-tree-map "#file-tree-graph")
       (render-tree-map tree-graph-data))
     :component-did-mount
     (fn []
       (render-tree-map tree-graph-data))
     :reagent-render
     (fn []
       [:div#file-tree-graph])}))

