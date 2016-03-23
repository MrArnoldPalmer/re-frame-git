(ns re-frame-git.components.nav-bar
  (:require [reagent.core :as reagent]
            [re-frame-git.components.nav-bar-item :refer [nav-bar-item]]))

(def container-style
  {:display "flex"
   :flex-direction "row"})

(defn nav-bar
  []
  [:div {:style container-style}
   [nav-bar-item "repositories" "/repositories"]
   [nav-bar-item "test2" "/"]])
