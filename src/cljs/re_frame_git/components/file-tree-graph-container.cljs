(ns re-frame-git.components.file-tree-graph-container
  (:require [re-frame.core :as re-frame]
            [re-frame-git.components.file-tree-graph :as file-tree-graph]))

(defn file-tree-graph-container []
    (let [tree (re-frame/subscribe [:repo-tree])]
      [:div
       [file-tree-graph/main @tree]]))
