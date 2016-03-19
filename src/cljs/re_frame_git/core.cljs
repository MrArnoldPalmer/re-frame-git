(ns re-frame-git.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [re-frame-git.handlers]
              [re-frame-git.subs]
              [re-frame-git.components.core :refer [main]]
              [re-frame-git.config :as config]))

(when config/debug?
  (println "dev mode"))

(when config/debug?
  (reagent-dev-tools.state-tree/register-state-atom "App" re-frame.db/app-db))

(defn mount-root []
  (if config/debug?
    (reagent/render [:div
                     [main]
                     [reagent-dev-tools.core/dev-tool {}]]
                    (.getElementById js/document "app"))
    (reagent/render [main]
                    (.getElementById js/document "app"))))

(defn ^:export init [] 
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
