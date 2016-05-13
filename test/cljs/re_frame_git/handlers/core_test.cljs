(ns re-frame-git.handlers.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [re-frame-git.handlers.core :as handlers]))

(deftest set-current-route
  (testing "sets :current-route key in app state to route string parameter"
    (let [route-name "route-string"
          result (handlers/set-current-route {} [route-name])]
      (is (= (:current-route result) route-name)))))

(deftest api-error
  (testing "prints error to console, returns map parameter as passed in"
    (let [db {:test "test"}
          result (handlers/api-error db ["response"])]
      (is (= result db)))))

