(ns reframe-git.components.languages
  (:require [re-frame.core :as re-frame]
            [reframe-git.components.languages-tree :as languages-tree]))

(defn main []
  (let [languages (re-frame/subscribe [:repo-languages])]
    (println @languages)
    [:div
     [languages-tree/main languages]]))
