(ns reframe-git.components.languages
  (:require [re-frame.core :as re-frame]
            [reframe-git.components.languages-graph :as languages-graph]))

(defn main []
  (let [languages (re-frame/subscribe [:repo-languages])]
    (println @languages)
    [:div
     [languages-graph/main languages]]))
