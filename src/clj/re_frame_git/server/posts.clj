(ns re-frame-git.server.posts
  (:require [schema.core :as schema]
            [monger.core :as mg]
            [monger.collection :as mc]
            [re-frame-git.db.core :refer [db]])
  (:import [org.bson.types ObjectId]))
            
(def schema
  "Schema for Posts Map"
  {:name schema/Str
   :text schema/Str
   :tags [schema/Str]
   :date schema/Str})

(defn save-post
  "Save post map argument to mongodb"
  [post-map]
  (schema/validate schema
              post-map)
  (mc/insert-and-return db "posts" (assoc post-map :_id (str (ObjectId.)))))

(defn get-posts
  "Get all posts in db"
  []
  (mc/find-maps db "posts"))

(defn get-post-by-id
  [id]
  (mc/find-map-by-id db "posts" id))
