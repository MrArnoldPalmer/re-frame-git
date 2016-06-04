(ns re-frame-git.repo-details.subs
  (:require-macros [reagent.ratom :as ratom])
  (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
  :repo-list
  (fn [db]
    (ratom/reaction (:repo-list @db))))

