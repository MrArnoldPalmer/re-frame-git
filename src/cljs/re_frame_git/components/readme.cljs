(ns re-frame-git.components.readme
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [markdown.core :refer [md->html]]))

(defn readme
  [readme-markdown]
  [:div
   {:dangerouslySetInnerHTML {:__html (md->html readme-markdown)}}])    
