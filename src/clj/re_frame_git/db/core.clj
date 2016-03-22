(ns re-frame-git.db.core
  (:require [monger.core :as mg]))

(def conn (mg/connect {:host (System/getenv "MONGO_PORT_27017_TCP_ADDR")}))
(def db (mg/get-db conn "re-frame-git"))
