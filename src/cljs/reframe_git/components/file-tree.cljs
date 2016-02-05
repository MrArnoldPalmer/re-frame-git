(ns reframe-git.components.file-tree
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn main
  []
  (reagent/create-class
    {:display-name "file-tree"
     :component-did-mount
     #(println "file-tree did mount")
     :reagent-render
     (fn []
       (let [tree (re-frame/subscribe [:repo-tree])]
         [:div
          (keys @tree)]))}))
