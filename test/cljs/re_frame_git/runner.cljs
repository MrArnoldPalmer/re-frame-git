(ns re-frame-git.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [re-frame-git.handlers.test]
            [re-frame-git.repositories.handlers.test]
            [re-frame-git.repo-details.handlers.test]
            [re-frame-git.utils.core-test]))

(doo-tests 're-frame-git.handlers.test
           're-frame-git.repositories.handlers.test
           're-frame-git.repo-details.handlers.test)
