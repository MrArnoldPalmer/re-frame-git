(ns re-frame-git.repositories.handlers.test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [re-frame-git.repositories.handlers :as handlers]
            [re-frame-git.utils.core :refer [build-repo-keyword]]))

(def test-username "username")
(def test-repo-name "repo-name")
(def test-repo-keyword
  (build-repo-keyword test-username test-repo-name))

(deftest set-repo-list
  (testing "checks if repo-list for user is currnet returns db"
    (let [db {:repo-list {:github-username test-username :items ["item"]}};}])))
          result (handlers/set-repo-list db test-username)]
     (is (= result db)))))

(deftest get-repo-list
  (testing "returns map as passed in"
    (let [db {:test "test"}
          result (handlers/get-repo-list db [])]
      (is (= result (assoc db :repo-list {:loading true}))))))

(deftest process-repo-list-response
  (testing "returns map with response maps associated with :repo-list in map parameter"
    (let [repo-list "repo-list"
          db (handlers/process-repo-list-response {} [test-username repo-list])]
      (is (= (:repo-list db) {:loading false :items repo-list :github-username test-username})))))
