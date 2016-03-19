(ns re-frame-git.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [re-frame-git.handlers]
              [re-frame-git.subs]
              [re-frame-git.components.core :refer [main]]
              [re-frame-git.config :as config]
              [reagent-dev-tools.core :as dev-tools]
              [reagent-dev-tools.state-tree :as dev-state]))

(when config/debug?
  (println "dev mode"))

(when config/debug?
  (dev-state/register-state-atom "App" re-frame.db/app-db))

(defn mount-root []
  (if config/debug?
    (reagent/render [:div
                     [main]]
                    (.getElementById js/document "app"))
    (reagent/render [main]
                    (.getElementById js/document "app"))))

(defn ^:export init [] 
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
