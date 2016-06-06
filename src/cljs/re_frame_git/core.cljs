(ns re-frame-git.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [re-frame-git.handlers]
            [re-frame-git.repositories.handlers]
            [re-frame-git.repo-details.handlers]
            [re-frame-git.subs]
            [re-frame-git.repositories.subs]
            [re-frame-git.repo-details.subs]
            [re-frame-git.views :as views]
            [re-frame-git.config :refer [debug?]]
            [re-frame-git.routes :as routes]
            [reagent-dev-tools.core :as dev-tools]
            [reagent-dev-tools.state-tree :as dev-state]
            [cljsjs.d3]
            [cljsjs.material]
            [cljsjs.highlight]
            [cljsjs.highlight.langs.bash]
            [cljsjs.highlight.langs.clojure]
            [cljsjs.highlight.langs.javascript]
            [cljsjs.highlight.langs.xml]))

(when debug?
  (println "dev mode"))

(when debug?
  (dev-state/register-state-atom "App" re-frame.db/app-db))

(defn mount-root []
  (if debug?
    (reagent/render
      [:div
       [views/main]
       [dev-tools/dev-tool {}]]
      (.getElementById js/document "app"))
    (reagent/render [views/main] (.getElementById js/document "app"))))

(defn ^:export init [] 
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
