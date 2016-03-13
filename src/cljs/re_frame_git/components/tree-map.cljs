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

(def tree-map-graph
  (-> js/d3
      (.-layout)
      (.treemap)
      (.size (clj->js [(- (.-innerWidth js/window) 40) (- (.-innerHeight js/window) 40)]))
      (.sticky true)
      (.value (fn [d]
                (.-size d)))))

(def color
  (-> js/d3
      (.-scale)
      (.category20c)))

(defn render-tree-map
  [tree-map-data-map]
  (let [div (-> js/d3
                (.select "#file-tree-graph")
                (.append "div")
                (.style "position" "relative"))
        node (-> div
                 (.datum (clj->js tree-map-data-map))
                 (.selectAll ".node")
                 (.data (.-nodes tree-map-graph))
                 (.enter)
                 (.append "div")
                 (.attr "class" "node")
                 (.transition)
                 (.duration 1500)
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
     :component-will-receive-props
     (fn [_ [_ new-data]]
       (println "did update")
       (println new-data)
       (render-tree-map new-data))
     :component-did-mount
     (fn []
       (render-tree-map tree-graph-data))
     :render
     (fn []
       [:div#file-tree-graph])}))

