(ns re-frame-git.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [re-frame-git.handlers.core]
              [re-frame-git.subs]
              [re-frame-git.components.application :refer [application]]
              [re-frame-git.config :as config]
              [re-frame-git.routes :refer [hook-browser-navigation!]]
              [reagent-dev-tools.core :as dev-tools]
              [reagent-dev-tools.state-tree :as dev-state]))

(when config/debug?
  (println "dev mode"))

(when config/debug?
  (dev-state/register-state-atom "App" re-frame.db/app-db))

(defn mount-root []
  (if config/debug?
    (reagent/render [:div
                     [application]
                     [dev-tools/dev-tool {}]]
                    (.getElementById js/document "app"))
    (reagent/render [application]
                    (.getElementById js/document "app"))))

(defn ^:export init [] 
  (hook-browser-navigation!)
  (re-frame/dispatch-sync [:initialize-db])
  (re-frame/dispatch [:get-posts])
  (mount-root))
