(ns re-frame-git.components.loading-indicator
  (:require [re-frame.core :as re-frame]
            [re-com.core :refer [throbber]]))

(defn loading-indicator
  []
  [throbber
   :size :large])
