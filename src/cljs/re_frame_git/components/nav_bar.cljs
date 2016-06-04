(ns re-frame-git.components.nav-bar
  (:require [re-frame.core :as re-frame]
            [re-frame-git.components.nav-bar-item :refer [nav-bar-item]]
            [re-frame-git.routes :refer [home-route repositories-route]]))

(defn nav-bar
  []
  [:header {:class "mdl-layout__header"}
   [:div.mdl-layout__header-row
    [:span.mdl-layout-title "Re-Frame Git"]
    [:div.mdl-layout-spacer]
    [:nav {:class "mdl-navigation mdl-layout--large-screen-only"}
     [:a.mdl-navigation__link
      {:href (home-route)}
      "Home"]
     [:a.mdl-navigation__link
      {:href (repositories-route
               {:github-username
                "mrarnoldpalmer"})}
      "Repositories"]]]])
