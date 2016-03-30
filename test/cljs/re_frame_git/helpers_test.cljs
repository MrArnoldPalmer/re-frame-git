(ns re-frame-git.helpers-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [re-frame-git.helpers :as helpers]))

(deftest GET
  (testing "makes an http GET request to URL passed in using cljs-http and core.ajax"))
