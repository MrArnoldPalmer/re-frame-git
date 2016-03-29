(ns re-frame-git.components.nav-bar
  (:require [re-frame.core :as re-frame]
            [re-frame-git.components.nav-bar-item :refer [nav-bar-item]]
            [re-frame-git.routes :refer [home-route repositories-route]]))

(def container-style
  {:display "flex"
   :flex-direction "row"})

(defn nav-bar
  []
  (let [github-username (re-frame/subscribe [:github-username])]
    (println @github-username)
    [:div {:style container-style}
     [nav-bar-item "home" (home-route)]
     [nav-bar-item "repositories" (repositories-route {:github-username @github-username})]
     [nav-bar-item "blog" "/#"]]))
