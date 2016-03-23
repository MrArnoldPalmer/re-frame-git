(ns re-frame-git.components.nav-bar
  (:require [reagent.core :as reagent]
            [re-frame-git.components.nav-bar-item :refer [nav-bar-item]]))

(defn nav-bar
  []
  [:div {:style container-style}
   [nav-bar-item "test" "/"]
   [nav-bar-item "test2" "/"]])
