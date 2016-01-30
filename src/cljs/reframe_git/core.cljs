(ns reframe-git.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [reframe-git.handlers]
              [reframe-git.subs]
              [reframe-git.views :as views]
              [reframe-git.config :as config]))

(when config/debug?
  (println "dev mode"))

(defn mount-root []
  (reagent/render [views/application]
                  (.getElementById js/document "app")))

(defn ^:export init [] 
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
