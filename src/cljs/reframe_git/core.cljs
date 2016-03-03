(ns reframe-git.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [reframe-git.handlers]
              [reframe-git.subs]
              [reframe-git.components.core :as application]
              [reframe-git.config :as config]
              [reagent-dev-tools.core :as dev-tools]
              [reagent-dev-tools.state-tree :as dev-state]))

(when config/debug?
  (println "dev mode"))

(when config/debug?
  (dev-state/register-state-atom "App" re-frame.db/app-db))

(if config/debug?
  (do
    (defn mount-root []
      (reagent/render [:div [application/main] [dev-tools/dev-tool {}]]
                      (.getElementById js/document "app"))))
  (do
    (defn mount-root []
      (reagent/render [application/main]
                      (.getElementById js/document "app")))))

(defn ^:export init [] 
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
