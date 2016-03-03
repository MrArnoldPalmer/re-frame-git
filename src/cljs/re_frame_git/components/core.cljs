(ns re-frame-git.components.core
  (:require [re-frame.core :as re-frame]
            [re-frame-git.components.repo-details :refer [repo-details]]))

(defn main []
  [:div
   [repo-details]])
