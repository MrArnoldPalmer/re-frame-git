(ns re-frame-git.routes
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary]
            [goog.events :as events]
            [goog.history.EventType :as EventType])
  (:import goog.History))

(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (hook-browser-navigation!)
  (secretary/set-config! :prefix "#")
  (secretary/defroute home "/" []
    (re-frame/dispatch [:set-current-route "home"]))
  (secretary/defroute repositories "/repositories/:github-username" [github-username]
    (re-frame/dispatch [:set-current-route "repositories"])
    (re-frame/dispatch [:set-repo-list github-username]))
  (secretary/defroute
    repo-details
    "/repositories/:github-username/:repo-name"
    [github-username repo-name]
    (re-frame/dispatch [:set-current-route "repo-details"])
    (re-frame/dispatch [:set-current-repo github-username repo-name])))
