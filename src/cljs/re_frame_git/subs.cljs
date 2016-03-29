(ns re-frame-git.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
  :repo-list
  (fn [db]
    (reaction (:repo-list @db))))

(re-frame/register-sub
  :current-route
  (fn [db]
    (reaction (:current-route @db))))

(re-frame/register-sub
  :github-username
  (fn [db]
    (reaction (:github-username @db))))

(re-frame/register-sub
  :repo-details
  (fn [db]
    (reaction (:repo-details @db))))

(re-frame/register-sub
  :repo-languages
  (fn [db]
    (reaction (:repo-languages @db))))

(re-frame/register-sub
  :repo-tree
  (fn [db]
    (reaction (:repo-tree @db))))
