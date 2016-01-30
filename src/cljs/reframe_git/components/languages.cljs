(ns reframe-git.components.languages
  (:require [re-frame.core :as re-frame]))

(defn main []
  (let [languages (re-frame/subscribe [:repo-languages])]
    (println @languages)
    [:div
     (keys @languages)]))
