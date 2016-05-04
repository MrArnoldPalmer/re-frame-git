(ns re-frame-git.db.core
  (:require [monger.core :as mg]))

(def db (:db (mg/connect-via-uri (System/getenv "DATABASE_URL"))))
