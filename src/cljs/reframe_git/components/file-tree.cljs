(ns reframe-git.components.file-tree
  (:require [re-frame.core :as re-frame]
            [reframe-git.components.file-tree-graph :as file-tree-graph]))

(defn main []
    (let [tree (re-frame/subscribe [:repo-tree])]
      [:div
       [file-tree-graph/main @tree]]))
