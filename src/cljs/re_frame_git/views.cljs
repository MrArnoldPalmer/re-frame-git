(ns re-frame-git.views
  (:require [re-frame.core :as re-frame]
            [re-frame-git.home.views :as home]
            [re-frame-git.repositories.views :as repositories]
            [re-frame-git.repo-details.views :as repo-details]
            [re-frame-git.routes :as routes]
            [reagent.core :as r]
            [secretary.core :as secretary]))

(defn find-repo []
  (let [repo-name (r/atom "")]
    (fn []
      [:input.mdl-textfield__input
       {:type "text"
        :name "search"
        :id "fixed-header-drawer-exp"
        :placeholder "username/repo"
        :on-change #(reset! repo-name (-> % .-target .-value))}])))

(defn nav-bar []
  [:header {:class "mdl-layout__header"}
   [:div.mdl-layout__header-row
    [:span.mdl-layout-title "Re-Frame Git"]
    [:div.mdl-layout-spacer]
    [:div {:class "mdl-textfield mdl-js-textfield mdl-textfield--expandable mdl-textfield--floating-label mdl-textfield--align-right"}
     [:label {:class "mdl-button mdl-js-button mdl-button--icon"
              :for "fixed-header-drawer-exp"}
      [:icon {:class "material-icons"} "search"]]
     [:div.mdl-textfield__expandable-holder
      [find-repo]]]]])

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
  (let [current-route (re-frame/subscribe [:current-route])]
    [:div {:class "mdl-layout mdl-js-layout mdl-layout--fixed-header"}
     [nav-bar]
     [nav-drawer]
     [:main.mdl-layout__content
      [:div.page-content
       (current-component @current-route)]]]))
