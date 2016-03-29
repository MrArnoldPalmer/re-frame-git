(ns re-frame-git.components.nav-bar-item
  (:require [reagent.core :as reagent]))

(defn nav-bar-item
  [text url]
  [:a {:href url}
   text])
