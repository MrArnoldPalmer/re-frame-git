(ns re-frame-git.home.views
  (:require [reagent.core :as reagent]))

(defn main
  []
  [:div
   [:p "Home"]
   [:button {:class "mdl-button mdl-js-button mdl-button--fab mdl-button--colored"}
    [:i {:class "material-icons"} "add"]]])
