(ns re-frame-git.styles.core
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:body {:margin "auto"
          :position "relative"
          :padding "20px"
          :height "100%"}]
  [:.node {:border "solid 1px white"
           :line-height 0.95
           :overflow "hidden"
           :position "absolute"
           :border-radius "6px"}])
