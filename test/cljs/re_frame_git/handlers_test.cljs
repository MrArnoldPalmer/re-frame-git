(ns re-frame-git.handlers-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [re-frame-git.handlers :as handlers]
            [ajax.core :as ajax]
            [re-frame-git.test-utils :refer [mock-ajax-success]]))

(deftest build-api-url
  (testing "returns an api endpoint url based on the parameter string argument"
    (let [url (handlers/build-api-url "/endpoint")]
      (is (= (str "https://api.github.com" "/endpoint") url)))))

(deftest GET
  (testing "makes an http GET request to URL passed in using core.ajax"
    (with-redefs [ajax/GET #(mock-ajax-success [%1 %2])]
      (let [url "test-url"
            handler "success-function"
            error "error-function"
            response (handlers/GET url handler error)]
        (is (= (:status response) 200))
        (is (= (get-in response [:options 0]) url))
        (is (= (get-in response [:options 1 :handler]) handler))
        (is (= (get-in response [:options 1 :error-handler]) error))
        (is (= (get-in response [:options 1 :response-format]) :json))
        (is (= (get-in response [:options 1 :keywords?]) true))))))

(deftest api-error
  (testing "prints error to console, returns map parameter as passed in"
    (let [db {:test "test"}
          result (handlers/api-error db [_ "response"])]
      (is (= result db)))))

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
          db (handlers/process-repo-branches-response {} [_ _ repo-branches])]
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

