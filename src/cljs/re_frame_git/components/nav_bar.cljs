(ns re-frame-git.components.nav-bar
  (:require [re-frame.core :as re-frame]
            [re-com.core :refer [h-box gap]]
            [re-frame-git.components.nav-bar-item :refer [nav-bar-item]]
            [re-frame-git.routes :refer [home-route repositories-route]]))

(defn nav-bar
  []
  (let [github-username (re-frame/subscribe [:github-username])]
   [h-box
    :width "100%"
    :children [[nav-bar-item "home" (home-route)]
               [gap :size "1"]
               [nav-bar-item "repositories" (repositories-route {:github-username @github-username})]
               [gap :size "1"]
               [nav-bar-item "blog" "/#"]]]))
