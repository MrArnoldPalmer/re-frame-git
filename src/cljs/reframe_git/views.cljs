(ns reframe-git.views
    (:require [re-frame.core :as re-frame]))


(defn get-repo []
  (fn []
    (let [repo-name (atom "")]
      [:div
        [:input {:type "text"
                 :value @repo-name
                 :on-change #(reset! repo-name (-> % .-target .-value))}]
        [:button {:on-click #(re-frame/dispatch (:get-repo @repo-name))}
         "Get Repo"]])))

(defn repo-details []
  (let [repo (re-frame/subscribe [:repo-details])]
    (println repo)
    [:div
     (:full_name @repo)
     [get-repo]]))

