(ns reframe-git.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

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

(re-frame/register-sub
  :db
  (fn [db]
    (reaction @db)))
