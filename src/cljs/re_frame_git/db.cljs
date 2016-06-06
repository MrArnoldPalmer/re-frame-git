(ns re-frame-git.db
  (:require [re-frame-git.repositories.db :as repositories-db]
            [re-frame-git.repo-details.db :as repo-details-db]))

(def default-db
  (merge
    {:current-route ""
     :github-username nil}
    repositories-db/default-db
    repo-details-db/default-db))
