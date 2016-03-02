(ns reframe-git.components.file-tree-graph-container
  (:require [re-frame.core :as re-frame]
            [reframe-git.components.file-tree-graph :as file-tree-graph]))

(defn file-tree-graph-container []
    (let [tree (re-frame/subscribe [:repo-tree])]
      [:div
       [file-tree-graph/main @tree]]))
