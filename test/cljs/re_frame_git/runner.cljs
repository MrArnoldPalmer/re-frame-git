(ns re-frame-git.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [re-frame-git.core-test]))

(doo-tests 're-frame-git.core-test)
