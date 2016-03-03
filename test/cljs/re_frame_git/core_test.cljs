(ns re-frame-git.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [re-frame-git.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 2 2))))
