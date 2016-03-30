(ns re-frame-git.test-utils)

(defn mock-ajax-success
  [options]
  {:status 200
   :options options})
