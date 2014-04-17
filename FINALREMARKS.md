Final Remarks
===

#### Execution contexts

I cheated with `ExecutionContext`s, by importing them globally. This is not a good practice. Don't do it.

An `ExecutionContext` is needed for `map` and `flatMap` on `scala.concurrent.Future`, so also for `for` comprehensions with `Future`, and for the `Monad` instance for `Future`.

The `Monad` instance for `Future` (`scalaz.contrib.std.futureInstance`) is a `def` and it takes an `ExecutionContext` as implicit parameter. So if you import `futureInstance` and the compiler still complains that it can't find the `Functor` or `Monad` instance for `Future`, make sure that you have an implicit `ExecutionContext` in scope.

#### Stacking monad transformers
A Monad transformer is a monad, so you can stack 'em: put a monad transformer in a monad transformer, and you have three behaviours!

But doing this is a code smell: you're probably waiting too long with interpreting your effects, and thereby leaking too much details to outer layers.

#### Failing futures

Don't use failing `Future`s. 

`Future` is useful as a container that does async computations. But `scala.concurrent.Future` also works as a `Try`, which is basically an `Either` specialized so that the `Left` side is always `Throwable`. It's annoying to let Future deal with both. For futures that may fail in a way that you want to handle, convert them to `Future[Throwable \/ A]` when possible, and make the `Future` always successful.

#### Errors are context dependent

Errors depend on contexts. A `findById` method should probably return an `Option` and not a `\/`, because by itself there is no sensible error. 

Its caller probably dóes have that context, and can transform the option to a suitable error. For example, if the caller is a Play Action method, a suitable error would be a `NotFound` result.

#### What about Validation, you promised validation!

We've used `Validation` from Scala 6 a lot. But we used it the way that we used `\/` from Scalaz 7 in this tutorial.

In Scalaz 7, `Validation` is no longer a monad, and not usable in `for`-comprehensions. It's intended use is different, not for sequential execution but for aggregating failures. We should replace most uses of Scalaz 6 `Validation` by Scalaz 7 `\/`.

#### Not everything is a monad

There are laws. Certain properties must hold for the operations in order for them to form a monad. If these properties don't hold, all hell breaks loose: things we decided we could do because we're dealing with a monad are no longer true.

The laws are:

    // Left identity:
    Monad.point(a).flatMap(f) === f(a)

    // Right identity (explained for Option):
    m.flatMap(Monad.point(_)) === m
    
    // Associativity
    m.flatMap(f).flatMap(g) === m.flatMap(x => f(x).flatMap(g))
