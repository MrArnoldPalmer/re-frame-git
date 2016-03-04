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
        (println response)
        (println (get-in response [:options 1 :handler]))
        (is (= (:status response) 200))
        (is (= (get-in response [:options 0]) url))
        (is (= (get-in response [:options 1 :handler]) handler))
        (is (= (get-in response [:options 1 :error-handler]) error))
        (is (= (get-in response [:options 1 :response-format]) :json))
        (is (= (get-in response [:options 1 :keywords?]) true))))))
