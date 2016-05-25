(defproject re-frame-git "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.8.51"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [reagent "0.6.0-alpha"]
                 [re-frame "0.7.0"]
                 [re-com "0.8.3"]
                 [cljs-http "0.1.40"]
                 [garden "1.3.2"]
                 [cljsjs/d3 "3.5.16-0"]
                 [cljsjs/highlight "8.4-0"]
                 [secretary "1.2.3"]
                 [metosin/reagent-dev-tools "0.1.0"]
                 [compojure "1.5.0"]
                 [clj-http "3.0.1"]
                 [ring "1.4.0"]
                 [ring/ring-json "0.4.0"]
                 [com.novemberain/monger "3.0.2"]
                 [prismatic/schema  "1.1.1"]
                 [markdown-clj "0.9.89"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj" "src/cljs"]

  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-figwheel "0.5.2"]
            [lein-garden "0.2.6"]
            [lein-doo "0.1.6"]
            [lein-ring "0.9.7"]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"
                                    "test/js"
                                    "resources/public/css/compiled"]

  :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.2.1"]
                                  [org.clojure/tools.nrepl "0.2.12"]]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}
             :uberjar {:main re-frame-git.server.core
                       :source-paths ["src/clj" "src/cljs"]
                       :prep-tasks ["compile" ["cljsbuild" "once" "min"]]
                       :hooks [leiningen.garden]
                       :aot :all
                       :omit-source true}}

  :main ^:skip-aot re-frame-git.server.core
  :ring {:handler re-frame-git.server.core/app}

  :figwheel {:ring-handler re-frame-git.server.core/app
             :repl false
             :css-dirs ["resources/public/css"]}

  :garden {:builds [{:id "screen"
                     :source-paths ["src/clj"]
                     :stylesheet re-frame-git.styles.core/screen
                     :compiler {:output-to "resources/public/css/compiled/screen.css"
                                :pretty-print? true}}]}

  :doo {:build "test"}

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs" "src/clj"]
                        :figwheel {:on-jsload "re-frame-git.core/mount-root"}
                        :compiler {:main re-frame-git.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :optimizations :none
                                   :source-map true
                                   :source-map-timestamp true
                                   :verbose true}}

                       {:id "test"
                        :source-paths ["src/cljs" "src/clj" "test/cljs"]
                        :compiler {:output-to "resources/public/js/compiled/test.js"
                                   :main re-frame-git.runner
                                   :optimizations :none}}

                       {:id "min"
                        :source-paths ["src/cljs" "src/clj"]
                        :compiler {:main re-frame-git.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :optimizations :advanced
                                   :closure-defines {goog.DEBUG false}
                                   :pretty-print false
                                   :verbose true}}]}
  :uberjar-name "re-frame-git.jar"
  :uberjar-exclusions [#"test/cljs"])
