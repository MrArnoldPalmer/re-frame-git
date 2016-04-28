FROM clojure
ADD . /re-frame-git
WORKDIR /re-frame-git
RUN lein figwheel dev
