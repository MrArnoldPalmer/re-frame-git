(ns re-frame-git.middleware.core
  (:require [re-frame.core :refer [dispatch]]
            [re-frame-git.utils.core :refer [build-repo-keyword item-loaded]]))

(defn repo-is-loaded
  [handler]
  (fn is-loaded-handler
    [db args]
    (let [new-db (handler db args)
          username (get args 0)
          repo-name (get args 1)
          repo-state
          (get-in new-db [:repo-details
                          (build-repo-keyword username repo-name)])]
      (println (item-loaded repo-state))
      (if (item-loaded repo-state)
        (println "loaded: " repo-state)
        (println "not loaded: " repo-state))
      new-db)))

(defn load-repo
  [username repo-name]
  (dispatch [:get-repo username repo-name])
  (dispatch [:get-repo-branches username repo-name]))
