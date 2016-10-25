# Free-form-examples

This is an set of examples for the [Free-form library](https://github.com/pupeno/free-form),
designed to help develop it, but also demonstrate how to use it and it's
capabilities. You can access a [live version at http://free-form-examples.pupeno.com](http://free-form-examples.pupeno.com).

To run it on your own computer, check out this repository and then simply run:

```bash
lein clean
lein figwheel
```

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Free-form snapshot

To run against the current snapshot of Free-form, make sure it's present in the
[checkouts directory](https://github.com/technomancy/leiningen/blob/master/doc/TUTORIAL.md#checkout-dependencies).
You can clone it directly there:

```bash
mkdir checkouts
cd checkouts
git clone git@github.com:pupeno/free-form.git
```

or symlink to wherever it is:

```bash
mkdir checkouts
cd checkouts
ln -s /path/to/free-form
```

To automatically re-load Free-form's source code whenever you change it, you
can add it's directory to project.clj:

```clojure
:cljsbuild {:builds [{:source-paths ["src/cljs" "checkouts/free-form/src/cljs"]}
                     #_other-stuff]}
```

## Deploying

```bash
lein uberjar
lein heroku deploy
```

by using https://devcenter.heroku.com/articles/deploying-clojure-applications-with-the-heroku-leiningen-plugin

## License

Copyright © 2015, 2016 José Pablo Fernández Silva, All rights reserved.

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
