(ns re-frame-git.containers.application
  (:require [re-frame.core :as re-frame]
            [re-com.core :refer [v-box]]
            [re-frame-git.components.nav-bar :refer [nav-bar]]
            [re-frame-git.containers.repositories :refer [repositories]]
            [re-frame-git.containers.home :refer [home]]))

(defn current-component
  [current-route]
  (cond
    (= current-route "home") [home]
    (= current-route "repositories") [repositories]
    :else nil))

(defn application []
  (let [current-route (re-frame/subscribe [:current-route])]
    [v-box
     :children [[nav-bar]
                (current-component @current-route)]]))
