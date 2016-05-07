(ns re-frame-git.handlers.post-handlers
  (:require [re-frame.core :refer [dispatch]]
            [re-frame-git.db :as db]
            [re-frame-git.utils.core :refer [GET]]))

(defn get-posts
  [db]
  (GET "/api/posts"
       #(dispatch [:process-posts-response %1])
       #(dispatch [:api-error %1]))
  db)

(defn process-posts-response
  [db [response]]
  (assoc-in db [:posts-list] response))
