(ns re-frame-git.middleware
  (:require [re-frame.core :refer [dispatch]]
            [re-frame-git.utils.core :refer [build-repo-keyword item-loaded]]))

(defn repo-loaded
  [handler]
  (fn is-loaded-handler
    [db args]
    (let [new-db (handler db args)
          username (get args 0)
          repo-name (get args 1)
          repo-state
          (get-in new-db [:repo-details
                          (build-repo-keyword username repo-name)])]
      (if (item-loaded repo-state)
        (dispatch [:set-current-repo username repo-name]))
      new-db)))
