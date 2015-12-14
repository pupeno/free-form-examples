# Free-form-examples

This is an set of examples for the [Free-form library](https://carouselapps.com/free-form), designed to help develop it,
but also demonstrate how to use it and it's capabilities. You can access a
[live version at http://free-form-examples.carouselapps.com](http://free-form-examples.carouselapps.com).

To run it on your own computer, check out this repository and then simply run:

```bash
lein clean
lein figwheel dev
```

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Free-form snapshot

To run against the current snapshot of Free-form, make sure it's present in the checkouts directory. You can clone it
directly there:

```bash
mkdir checkouts
cd checkouts
git clone git@github.com:carouselapps/free-form.git
```

or symlink to wherever it is:

```bash
mkdir checkouts
cd checkouts
ln -s /path/to/free-form
```

To automatically re-load Free-form's source code whenever you change it, you can add it's directory to project.clj:

```clojure
:cljsbuild {:builds [{:source-paths ["src/cljs" "checkouts/free-form/src/cljs"]}
                     #_other-stuff]}
```

## License

Copyright © 2015 Carousel Apps, Ltd. All rights reserved.

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
