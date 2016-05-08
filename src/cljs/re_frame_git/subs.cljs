(ns re-frame-git.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :refer [register-sub]]))

(register-sub
  :repo-list
  (fn [db]
    (reaction (:repo-list @db))))

(register-sub
  :current-route
  (fn [db]
    (reaction (:current-route @db))))

(register-sub
  :github-username
  (fn [db]
    (reaction (:github-username @db))))

(register-sub
  :repo-details
  (fn [db]
    (reaction (:repo-details @db))))

(register-sub
  :current-repo
  (fn [db]
    (reaction (:current-repo @db))))

(register-sub
  :repo-languages
  (fn [db]
    (reaction (:repo-languages @db))))

(register-sub
  :repo-tree
  (fn [db]
    (reaction (:repo-tree @db))))
