(ns reframe-git.components.core
  (:require [re-frame.core :as re-frame]
            [reframe-git.components.repo-details :refer [repo-details]]))

(defn main []
  [:div
   [repo-details]])
