(ns re-frame-git.components.core
 (:require [re-frame.core :as re-frame]
           [re-frame-git.components.repo-details-container :refer [repo-details-container]]))

(defn main []
  [:div
   [repo-details-container]])
