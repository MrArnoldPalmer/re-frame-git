(ns re-frame-git.views
  (:require [re-frame.core :refer [subscribe]]
            [re-frame-git.home.views :as home]
            [re-frame-git.repositories.views :as repositories]
            [re-frame-git.repo-details.views :as repo-details]
            [re-frame-git.routes :as routes]))

(defn nav-bar []
  [:header {:class "mdl-layout__header"}
   [:div.mdl-layout__header-row
    [:span.mdl-layout-title "Re-Frame Git"]
    [:div.mdl-layout-spacer]
    [:nav {:class "mdl-navigation mdl-layout--large-screen-only"}
     [:a.mdl-navigation__link
      {:href (routes/home)}
      "Home"]
     [:a.mdl-navigation__link
      {:href (routes/repositories
               {:github-username
                "mrarnoldpalmer"})}
      "Repositories"]]]])

(defn nav-drawer []
  [:div.mdl-layout__drawer
   [:span.mdl-layout-title "Re-Frame Git"]
   [:nav.mdl-navigation
    [:a.mdl-navigation__link
     {:href (routes/home)}
     "Home"]
    [:a.mdl-navigation__link
     {:href (routes/repositories
              {:github-username
               "mrarnoldpalmer"})}
     "Repositories"]]])

(defn current-component
  [current-route]
  (cond
    (= current-route "home") [home/main]
    (= current-route "repositories") [repositories/main]
    (= current-route "repo-details") [repo-details/main]
    :else nil))

(defn main []
  (let [current-route (subscribe [:current-route])]
    [:div {:class "mdl-layout mdl-js-layout mdl-layout--fixed-header"}
     [nav-bar]
     [nav-drawer]
     [:main.mdl-layout__content
      [:div.page-content
       (current-component @current-route)]]]))
