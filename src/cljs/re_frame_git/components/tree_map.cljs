(ns re-frame-git.components.tree-map
   (:require [re-frame.core :as re-frame]
             [reagent.core :as reagent]))

(defn position
  []
  (this-as context
           (-> context
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

(defn render-tree-map
  [tree-map-data]
  (let [tree-map (-> js/d3
                     (.-layout)
                     (.treemap)
                     (.size (clj->js [(- (.-innerWidth js/window) 40) (- (.-innerHeight js/window) 40)]))
                     (.value (fn [d]
                              (.-size d))))
        color (-> js/d3
                  (.-scale)
                  (.category20c))
        div (-> js/d3
                (.select "#file-tree-graph")
                (.append "div")
                (.style "position" "relative"))]
    (-> div
        (.datum (clj->js tree-map-data))
        (.selectAll ".node")
        (.data (.-nodes tree-map))
        (.enter)
        (.append "div")
        (.attr "class" "node")
        (.transition)
        (.duration 1500)
        (.call position)
        (.style "background-color" (fn [d]
                                      (if (not (nil? (.-children d)))
                                        (color (.-name d)))))
        (.text (fn [d]
                 (if (nil? (.-children d))
                   (.-name d)))))))
                          

(defn tree-map
  [tree-graph-data]
  (reagent/create-class
    {:display-name "file-tree"
     :component-will-receive-props
     (fn [_ [_ new-data]]
       (render-tree-map new-data))
     :component-did-mount
     (fn []
       (render-tree-map tree-graph-data))
     :render
     (fn []
       [:div#file-tree-graph])}))

