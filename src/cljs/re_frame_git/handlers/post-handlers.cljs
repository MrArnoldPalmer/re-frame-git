(ns re-frame-git.handlers.post-handlers
  (:require [re-frame.core :as re-frame]
            [re-frame-git.db :as db]
            [re-frame-git.helpers :refer [GET]]))

(defn get-posts
  [db [_]]
  (GET "/api/posts"
       #(re-frame/dispatch [:process-posts-response %1])
       #(re-frame/dispatch [:api-error %1]))
  db)

(defn process-posts-response
  [db [_ response]]
  (assoc-in db [:posts-list] response))
