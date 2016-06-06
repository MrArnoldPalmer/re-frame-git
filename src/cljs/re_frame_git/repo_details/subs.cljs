(ns re-frame-git.repo-details.subs
  (:require-macros [reagent.ratom :as ratom])
  (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
 :repo-details
 (fn [db]
   (ratom/reaction (:repo-details @db))))

(re-frame/register-sub
  :current-repo
  (fn [db]
    (ratom/reaction (:current-repo @db))))

(re-frame/register-sub
  :repo-languages
  (fn [db]
    (ratom/reaction (:repo-languages @db))))

(re-frame/register-sub
  :repo-tree
  (fn [db]
    (ratom/reaction (:repo-tree @db))))
