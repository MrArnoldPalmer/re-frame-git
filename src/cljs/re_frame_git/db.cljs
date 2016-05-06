(ns re-frame-git.db)

(def default-db
  {:current-route ""
   :current-repo {:loading false
                  :details nil
                  :tree nil
                  :languages nil}
   :repo-details {}
   :repo-languages {}
   :repo-tree {:loading false
               :items nil}
   :posts-list {:loading false
                :items []}
   :repo-list {:loading false
               :items []
               :github-username nil}
   :github-username "mrarnoldpalmer"})
