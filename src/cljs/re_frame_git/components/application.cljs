(ns re-frame-git.components.application
  (:require [re-frame.core :as re-frame]
            [re-com.core :refer [v-box]]
            [re-frame-git.components.nav-bar :refer [nav-bar]]
            [re-frame-git.components.repositories :refer [repositories]]
            [re-frame-git.components.home :refer [home]]
            [re-frame-git.components.repo-details :refer [repo-details]]))

(defn current-component
  [current-route]
  (cond
    (= current-route "home") [home]
    (= current-route "repositories") [repositories]
    (= current-route "repo-details") [repo-details]
    :else nil))

(defn application []
  (let [current-route (re-frame/subscribe [:current-route])]
    [v-box
     :children [[nav-bar]
                (current-component @current-route)]]))
