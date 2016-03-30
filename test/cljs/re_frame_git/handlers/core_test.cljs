(ns re-frame-git.handlers.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [re-frame-git.handlers.core :as handlers]))

(deftest api-error
  (testing "prints error to console, returns map parameter as passed in"
    (let [db {:test "test"}
          result (handlers/api-error db [_ "response"])]
      (is (= result db)))))

