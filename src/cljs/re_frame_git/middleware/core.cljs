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
          (get-in new-db [:repo-details]
                      (build-repo-keyword username repo-name))]
      (if (item-loaded repo-state)
        (println "loaded: " repo-state)
        (println "not loaded"))
      (new-db))))
        ;(dispatch [:set-current-repo username repo-name])))))
