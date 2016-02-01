(ns reframe-git.components.application
  (:require [re-frame.core :as re-frame]
            [reframe-git.components.repo-details :as repo-details]))

(defn main []
  [:div
   [repo-details/main]])
