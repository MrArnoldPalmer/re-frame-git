(ns reframe-git.components.get-repo
    (:require [re-frame.core :as re-frame]))

(defn main []
  (fn []
    (let [repo-name (atom "")]
      [:div
        [:input {:type "text"
                 :on-change #(reset! repo-name (-> % .-target .-value))}]
        [:button {:on-click #(re-frame/dispatch [:get-repo @repo-name])}
         "Get Repo"]])))
