(ns re-frame-git.components.readme
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [markdown.core :refer [md->html]]
            [cljsjs.highlight]))

(defn readme
  [readme-markdown]
  (reagent/create-class
    {:display-name "readme"
     :component-did-mount
     #(.initHighlighting js/hljs)
     :render
     (fn []
       [:div
        {:dangerouslySetInnerHTML {:__html (md->html readme-markdown)}}])}))
