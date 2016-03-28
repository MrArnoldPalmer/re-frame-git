(ns re-frame-git.components.nav-bar-item
  (:require [reagent.core :as reagent]
            [re-frame-git.routes :refer [repositories-route]]))

(defn nav-bar-item
  [text url]
  [:a {:href (repositories-route {:username "mrarnoldpalmer"})}
   text])
