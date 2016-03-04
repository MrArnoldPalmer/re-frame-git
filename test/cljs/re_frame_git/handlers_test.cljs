(ns re-frame-git.handlers-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [re-frame-git.handlers :as handlers]
            [ajax.core :as ajax]
            [re-frame-git.test-utils :refer [mock-ajax-success]]))

(deftest build-api-url
  (testing "returns an api endpoint url based on the parameter string argument"
    (let [url (handlers/build-api-url "/endpoint")]
      (is (= (str "https://api.github.com" "/endpoint"))))))

(deftest GET
  (testing "makes an http GET request to URL passed in using core.ajax"
    (let [url "test-url"]
      (with-redefs [ajax/GET #(mock-ajax-success [%1 %2])]
        (println (handlers/GET url :success-function :error-function))))))
