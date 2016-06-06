(ns re-frame-git.subs
  (:require-macros [reagent.ratom :as ratom])
  (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
 :current-route
 (fn [db]
   (ratom/reaction (:current-route @db))))
