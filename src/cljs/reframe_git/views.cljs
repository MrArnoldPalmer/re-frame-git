(ns reframe-git.views
    (:require [re-frame.core :as re-frame]))

(defn main-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div "Hello from " @name
        [:div
          [:button {:on-click #(re-frame/dispatch )}"testing"]]])))

(defn repo-details []
  (let [repo (re-frame/subscribe [:repo-details])]
    (println repo)
    [:div (:full_name @repo)]))
