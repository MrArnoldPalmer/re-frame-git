(ns re-frame-git.containers.home
  (:require [re-frame.core :as re-frame]
            [re-frame-git.components.nav-bar :refer [nav-bar]]))

(defn home []
  [:div
   [nav-bar]])
