(ns re-frame-git.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [re-frame-git.handlers]
              [re-frame-git.subs]
              [re-frame-git.containers.home :refer [home]]
              [re-frame-git.config :as config]
              [goog.events :as events]
              [goog.history.EventType :as EventType]
              [secretary.core :as secretary :include-macros true]
              [reagent-dev-tools.core :as dev-tools]
              [reagent-dev-tools.state-tree :as dev-state])
    (:import goog.History))

(when config/debug?
  (println "dev mode"))

(when config/debug?
  (dev-state/register-state-atom "App" re-frame.db/app-db))

(defn mount-root []
  (if config/debug?
    (reagent/render [:div
                     [home]
                     [dev-tools/dev-tool {}]]
                    (.getElementById js/document "app"))
    (reagent/render [home]
                    (.getElementById js/document "app"))))

(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn ^:export init [] 
  (re-frame/dispatch-sync [:initialize-db])
  (re-frame/dispatch [:get-posts])
  (mount-root))
