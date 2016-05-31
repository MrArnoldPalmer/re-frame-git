(ns re-frame-git.components.home-container
  (:require [reagent.core :as reagent]))

(defn home-container
  []
  [:div
   [:p "Home"]
   [:button {:class "mdl-button mdl-js-button mdl-button--fab mdl-button--colored"}
    [:i {:class "material-icons"} "add"]]])
