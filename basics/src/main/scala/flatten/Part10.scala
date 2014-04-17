package flatten

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait Part10 {

  // Here's `FutureOption` again:
  case class FutureOption[A](contents: Future[Option[A]]) {
    def flatMap[B](fn: A => FutureOption[B]) = FutureOption {
      contents.flatMap {
        case Some(value) => fn(value).contents
        case None => Future.successful(None)
      }
    }

    def map[B](fn: A => B) = FutureOption {
      contents.map { option =>
        option.map(fn)
      }
    }
  }


  // Note that from the outer container, `Future`, we only use the following:
  // - The `map` method
  // - The `flatMap` method
  // - Creating a new one: `Future.successful`

  // As it turns out, these functionalities are part of a type class called `Monad`:

  trait Monad[F[_]] {
    def map[A, B](container: F[A])(function: A => B): F[B]
    def flatMap[A, B](container: F[A])(function: A => F[B]): F[B]
    def create[B](value: B): F[B]
  }

  // Instance for Future:
  val futureMonad = new Monad[Future] {
    override def map[A, B](container: Future[A])(function: A => B) = container.map(function)
    override def flatMap[A, B](container: Future[A])(function: A => Future[B]) = container.flatMap(function)
    override def create[B](value: B) = Future.successful(value)
  }

  // Exercise: create a `Monad` instance for `Option`


}