(ns re-frame-git.components.application-container
 (:require [re-frame.core :refer [subscribe]]
           [re-frame-git.components.nav-bar :refer [nav-bar]]
           [re-frame-git.components.nav-drawer :refer [nav-drawer]]
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
    [:div {:class "mdl-layout mdl-js-layout mdl-layout--fixed-header"}
     [nav-bar]
     [nav-drawer]
     [:main.mdl-layout__content
      [:div.page-content
       (current-component @current-route)]]]))
