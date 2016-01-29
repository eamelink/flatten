Flatten your code
===

This project is a hands-on tutorial with exercises and answers, that teaches how to flatten stacked containers like `Future[Option[A]]` so that they can be used in `for`-comprehensions.

There's an accompanying presentation: https://speakerdeck.com/eamelink/flatten-your-code

Prerequisites
---
* Some Scala experience
* Frustration with deeply nested `map` and `flatMap` structures.

Usage
--- 
Most of the code is not runnable: we only rely on the typechecker. That means it's critical to look at the code in an IDE (or [ENSIME](http://ensime.org)) that can show you the type of values.


Structure
---
There are two `sbt` projects: `basics` and `play-specific`. 

The first 14 parts are in `basics`. There is a package `flatten` with the tutorial and exercises, and a package `answers` with answers to the exercises.

The final 3 parts are in the `controllers` package of `play-specific`.

ToC
---

* Parts 1-5 introduce `for`-comprehensions.
* Parts 6 and 7 deal with nested containers.
* Parts 8-12 generalize the concept from part 7. These parts are *optional*, you can safely skip to part 13.
* Parts 13 and 14 continue with nested containers.
* Parts 15 to 17 show the concepts in a Play context.

Scalaz documentation
---
You can find Scalaz documentation on the http://docs.typelevel.org/
