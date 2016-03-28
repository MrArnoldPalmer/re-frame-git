(ns re-frame-git.routes
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary]
            [goog.events :as events]
            [goog.history.EventType :as EventType])
  (:import goog.History))

(secretary/set-config! :prefix "#")

(secretary/defroute home-route "/" []
  (re-frame/dispatch [:set-current-route "home"]))

(secretary/defroute repositories-route "/repositories/:username" [username]
  (re-frame/dispatch [:set-current-route "repositories"])
  (re-frame/dispatch [:get-repo-list username]))

(secretary/defroute posts-route "/posts" []
  (re-frame/dispatch [:set-current-route "posts"]))

(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))
