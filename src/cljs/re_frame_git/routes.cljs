(ns re-frame-git.routes
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary]
            [accountant.core :as accountant]))

(accountant/configure-navigation!
  {:nav-handler (fn [path]
                  (secretary/dispatch! path))
   :path-exists? (fn [path]
                   (secretary/locate-route path))})

(defn app-routes []
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
