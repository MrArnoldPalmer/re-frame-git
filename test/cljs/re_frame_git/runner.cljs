(ns re-frame-git.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [re-frame-git.core-test]
              [re-frame-git.handlers-test]
              [re-frame-git.components.file-tree-graph-test]))

(doo-tests 're-frame-git.core-test
           're-frame-git.handlers-test
           're-frame-git.components.file-tree-graph-test)
