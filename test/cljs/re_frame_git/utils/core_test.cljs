(ns re-frame-git.utils.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [re-frame-git.utils.core :as utils]))

(deftest GET
  (testing "Makes GET request using cljs-http and core.async and returns response body"))

(deftest item-loaded
  (testing "returns false with map argument which holds nil for value of any existing key"
    (let [result (utils/item-loaded {:a nil :b nil :c "string"})]
      (is (= result false))))
  (testing "returns true with map argument whose values for all existing keys are not nil"
    (let [result (utils/item-loaded {:a "string" :b 2 :c []})]
      (is (= result true)))))

(deftest build-repo-keyword
  (testing "takes username and repo-name strings and returns properly formatted lowercase full repo-name as keyword type")
  (let [result (utils/build-repo-keyword "username" "repo-name")]
    (is (= result :username/repo-name))))
