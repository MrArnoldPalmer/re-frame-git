# reframe-git
Web application providing data visualization for git repositories.

A [ClojureScript](https://github.com/clojure/clojurescript)/[re-frame](https://github.com/Day8/re-frame) application.

This application uses a [Leiningen](http://leiningen.org/) template.

## Development Mode

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build

```
lein clean
lein cljsbuild once min
```
