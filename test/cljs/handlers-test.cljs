(ns reframe-git.handlers-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [reframe-git.handlers :as handlers]))

(deftest build-api-url
  (testing "returns an api endpoint url based on the parameter string argument"
    (let [url (handlers/build-api-url "/endpoint")]
      (is (= (str "https://api.github.com" "/endpoint"))))))
