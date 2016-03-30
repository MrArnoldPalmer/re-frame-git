(ns re-frame-git.handlers.repo-handlers-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [re-frame-git.handlers.repo-handlers :as handlers]
            [re-frame-git.test-utils :refer [mock-ajax-success]]))

(deftest get-repo
  (testing "returns map as passed in"
    (let [db {:test "test"}
          result (handlers/get-repo db [_ _])]
      (is (= result db)))))

(deftest process-repo-response
  (testing "returns MAP with response maps associated with :repo-details in map parameter"
    (let [repo-details "repo-details"
          db (handlers/process-repo-response {} [_ _ repo-details])]
      (is (= (:repo-details db) repo-details)))))

(deftest get-repo-languages
  (testing "returns map as passed in"
    (let [db {:test "test"}
          result (handlers/get-repo-languages db [_ _])]
      (is (= result db)))))

(deftest process-repo-languages-response
  (testing "returns map with response maps associated with :repo-languages in map parameter"
    (let [repo-languages "repo-languages"
          db (handlers/process-repo-languages-response {} [_ _ repo-languages])]
      (is (= (:repo-languages db) repo-languages)))))

(deftest get-repo-branches
  (testing "returns map as passed in"
    (let [db {:test "test"}
          result (handlers/get-repo-branches db [_ _])]
      (is (= result db)))))

(deftest process-repo-branches-response
  (testing "returns map with response maps associated with :repo-branches in map parameter"
    (let [repo-branches "repo-branches"
          db (handlers/process-repo-branches-response {[:name "master" :commit {:sha "thing"}]} [_ _ repo-branches])]
      (println db)
      (is (= (:repo-branches db) repo-branches)))))

(deftest get-repo-tree
  (testing "returns map as passed in"
    (let [db {:test "test"}
          result (handlers/get-repo-tree db [_ _])]
      (is (= result db)))))

(deftest process-repo-tree-response
  (testing "returns map with response maps associated with :repo-tree in map parameter"
    (let [repo-tree "repo-tree"
          db (handlers/process-repo-tree-response {} [_ _ repo-tree])]
      (is (= (:repo-tree db) repo-tree)))))

