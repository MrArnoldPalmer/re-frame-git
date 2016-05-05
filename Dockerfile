FROM clojure
ADD . /re-frame-git
WORKDIR /re-frame-git
RUN lein deps
CMD lein figwheel dev & lein garden auto
