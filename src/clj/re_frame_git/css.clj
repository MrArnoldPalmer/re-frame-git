(ns re-frame-git.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:body {:margin "auto"
          :position "relative"
          :padding "20px"
          :height "100%"}
   :form {:position  "absolute"
          :right "10px"
          :top "10px"}
   :.node {:border "solid 1px white"
           :line-height 0.95
           :overflow "hidden"
           :position "absolute"
           :border-radius "6px"
           ;:background-image "-webkit-linear-gradient(top, hsla(0,0%,100%,.3), hsla(0,0%,100%,0))"
           ;:background-image "-moz-linear-gradient(top, hsla(0,0%,100%,.3), hsla(0,0%,100%,0))"
           ;:background-image "-ms-linear-gradient(top, hsla(0,0%,100%,.3), hsla(0,0%,100%,0))"
           ;:background-image "-o-linear-gradient(top, hsla(0,0%,100%,.3), hsla(0,0%,100%,0))"
           :background-image "linear-gradient(top, hsla(0,0%,100%,.3), hsla(0,0%,100%,0))"}])
