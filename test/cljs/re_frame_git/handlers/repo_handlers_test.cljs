(ns re-frame-git.handlers.repo-handlers-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [re-frame-git.handlers.repo-handlers :as handlers]
            [re-frame-git.test-utils :refer [mock-ajax-success]]
            [re-frame-git.utils.core :refer [build-repo-keyword]]))
(def test-username "username")
(def test-repo-name "repo-name")
(def test-repo-keyword
  (build-repo-keyword test-username test-repo-name))

(deftest get-repo-list
  (testing "returns map as passed in"
    (let [db {:test "test"}
          result (handlers/get-repo-list db [_ _])]
      (is (= result (assoc db :repo-list {:loading true}))))))

(deftest process-repo-list-response
  (testing "returns map with response maps associated with :repo-list in map parameter"
    (let [repo-list "repo-list"
          db (handlers/process-repo-list-response {} [test-username repo-list])]
      (is (= (:repo-list db) {:loading false :items repo-list :github-username test-username})))))

(deftest get-repo
  (testing "returns map as passed in"
    (let [db {:test "test"}
          result (handlers/get-repo db [_ _])]
      (is (= result db)))))

(deftest process-repo-response
  (testing "returns MAP with response maps associated with :repo-details in map parameter"
    (let [repo-details "repo-details"
          db (handlers/process-repo-response {} [test-username test-repo-name repo-details])]
      (is (= (get-in db [:repo-details test-repo-keyword :details]) repo-details)))))

(deftest get-repo-languages
  (testing "returns map as passed in"
    (let [db {:test "test"}
          result (handlers/get-repo-languages db [_ _])]
      (is (= result db)))))

(deftest process-repo-languages-response
  (testing "returns map with response maps associated with :repo-languages in map parameter"
    (let [repo-languages "repo-languages"
          db (handlers/process-repo-languages-response {} [test-username test-repo-name repo-languages])]
      (is (= (get-in db [:repo-details test-repo-keyword :languages]) repo-languages)))))

(deftest get-repo-branches
  (testing "returns map as passed in"
    (let [db {:test "test"}
          result (handlers/get-repo-branches db [_ _])]
      (is (= result db)))))

(deftest process-repo-branches-response
  (testing "returns map with response maps associated with :repo-branches in map parameter"
    (let [repo-branches [{:name "master" :commit {:sha "sha-string"}}]
          db (handlers/process-repo-branches-response {} [test-username test-repo-name repo-branches])]
      (is (= (get-in db [:repo-details test-repo-keyword :branches]) repo-branches)))))

(deftest get-repo-tree
  (testing "returns map as passed in"
    (let [db {:test "test"}
          result (handlers/get-repo-tree db [_ _])]
      (is (= result db)))))

(deftest process-repo-tree-response
  (testing "returns map with response maps associated with :repo-tree in map parameter"
    (let [repo-tree "repo-tree"
          db (handlers/process-repo-tree-response {} [test-username test-repo-name repo-tree])]
      (is (= (get-in db [:repo-details test-repo-keyword :tree]) repo-tree)))))

