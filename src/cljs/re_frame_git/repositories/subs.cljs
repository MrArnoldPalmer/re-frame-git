(ns re-frame-git.repositories.subs
 (:require-macros [reagent.ratom :as ratom])
 (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
  :repo-list
  (fn [db]
    (ratom/reaction (:repo-list @db))))

