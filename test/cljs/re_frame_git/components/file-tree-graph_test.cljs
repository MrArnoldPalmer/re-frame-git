(ns re-frame-git.components.file-tree-graph-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [re-frame-git.components.file-tree-graph :as file-tree-graph]))

(deftest format-tree-graph-data
  (testing "returns nested map representing file tree structure from flat vector of maps"))
