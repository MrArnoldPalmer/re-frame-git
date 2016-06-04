(ns re-frame-git.components.nav-drawer
  (:require [re-frame-git.routes :refer [home-route repositories-route]]))

(defn nav-drawer
  []
  [:div.mdl-layout__drawer
   [:span.mdl-layout-title "Re-Frame Git"]
   [:nav.mdl-navigation
    [:a.mdl-navigation__link
     {:href (home-route)}
     "Home"]
    [:a.mdl-navigation__link
     {:href (repositories-route
              {:github-username
               "mrarnoldpalmer"})}
     "Repositories"]]])
