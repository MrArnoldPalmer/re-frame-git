(ns re-frame-git.components.application-container
  (:require [re-frame.core :refer [subscribe]]
            [re-com.core :refer [v-box]]
            [re-frame-git.components.nav-bar :refer [nav-bar]]
            [re-frame-git.components.repositories-container :refer [repositories-container]]
            [re-frame-git.components.home-container :refer [home-container]]
            [re-frame-git.components.repo-details-container :refer [repo-details-container]]))

(defn current-component
  [current-route]
  (cond
    (= current-route "home") [home-container]
    (= current-route "repositories") [repositories-container]
    (= current-route "repo-details") [repo-details-container]
    :else nil))

(defn application-container []
  (let [current-route (subscribe [:current-route])]
    [v-box
     :children [[nav-bar]
                (current-component @current-route)]]))
