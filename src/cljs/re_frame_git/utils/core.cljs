(ns re-frame-git.utils.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<!]]
            [cljs-http.client :as client]))

(defn GET
  "Generic http GET function for handlers"
  [endpoint success-handler error-handler]
  (go (let [response (<! (client/get endpoint))]
        (if (:success response)
          (success-handler (:body response))
          (error-handler response)))))
