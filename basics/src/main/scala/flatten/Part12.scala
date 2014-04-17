package flatten

trait Part12 {

  // In the previous parts we've built our own `FutureOption` container, and then generalized that
  // to an `AnyMonadOption` container, that combines Option with any other monad.

  // Scalaz defines a similar thing, and calls it 'Option Transformer'. The class is named `OptionT`.

  // Similarly, there is also an `EitherT`, an Either Transformer, which combines a \/ (so not a scala.Either)
  // with any other monad.

  // Keep in mind that an OptionT works when the Option is the *inner* container, so Future[Option[A]], and not Option[Future[A]].
  // Similarly, an EitherT works with Future[String \/ A] or Option[Throwable \/ A] but not String \/ Option[A]


  // The Monad typeclass that we defined looks like:
  trait Monad[F[_]] {
    def map[A, B](container: F[A])(function: A => B): F[B]
    def flatMap[A, B](container: F[A])(function: A => F[B]): F[B]
    def create[B](value: B): F[B]
  }


  // But it turns out that we can defined `map` in terms of `flatMap` and `create`.
  trait Monad2[F[_]] {
    def flatMap[A, B](container: F[A])(function: A => F[B]): F[B]
    def create[B](value: B): F[B]

    def map[A, B](container: F[A])(function: A => B): F[B] = flatMap(container)(function andThen create)
  }

  // This means that if we define an instance, we only need to define `flatMap` and `create`.

  // flatMap is called 'bind' in Scalaz. It also often has the symbol >>=
  // create is called 'point' in Scalaz.


  // We called the implicit Monad instance parameter `monadInstanceForF`. Scalaz typically call these instances the same as the type they are for:
  // Our version:    (implicit monadInstanceForF: Monad[F])
  // Typical Scalaz: (implicit F: Monad[F])


  // Scalaz calls the 'contents' parameter 'run', so you can do: myTransformer.run to get the original structure out.
}