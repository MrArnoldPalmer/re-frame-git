(ns re-frame-git.repo-details.db)

(def default-db
  {:current-repo {:loading false
                  :details nil
                   :tree nil
                   :languages nil}
    :repo-details {}
    :repo-languages {}
    :repo-tree {:loading false
                :items nil}})
