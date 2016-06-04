(ns re-frame-git.utils.core
 (:require-macros [cljs.core.async.macros :refer [go]])
 (:require [cljs.core.async :refer [<!]]
           [cljs-http.client :as client]
           [clojure.string :refer [lower-case]]
           [re-frame.core :as re-frame]
           [re-frame-git.config :as config]))

(defn GET
  "Generic http GET function for handlers"
  ([endpoint success-handler error-handler] (GET endpoint {} success-handler error-handler))
  ([endpoint options success-handler error-handler]
   (go (let [response (<! (client/get endpoint (if (nil? options) {} options)))]
         (if (:success response)
           (success-handler (:body response))
           (error-handler response))))))

(defn item-loaded
  [item-details]
  (empty? (filter #(nil? (get % 1)) item-details)))

(defn build-repo-keyword
  [username repo-name]
  (keyword (lower-case (str username "/" repo-name))))
