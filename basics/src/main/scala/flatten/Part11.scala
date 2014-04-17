package flatten

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait Part11 {

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

  // We've seen that we only use the properties of a `Monad` from the `Future` here, so we can generalize `FutureOption`:

  trait Monad[F[_]] {
    def map[A, B](container: F[A])(function: A => B): F[B]
    def flatMap[A, B](container: F[A])(function: A => F[B]): F[B]
    def create[B](value: B): F[B]
  }

  case class AnyMonadOption[F[_], A](contents: F[Option[A]])(implicit monadInstanceForF: Monad[F]) {
    def flatMap[B](fn: A => AnyMonadOption[F, B]) = AnyMonadOption[F, B] {
      monadInstanceForF.flatMap(contents){
        case Some(value) => fn(value).contents
        case None => monadInstanceForF.create(None)
      }
    }

    def map[B](fn: A => B) = AnyMonadOption[F, B] {
      monadInstanceForF.map(contents){ option =>
        option.map(fn)
      }
    }
  }

  // So now you can not only use it for Future[Option[Int], but for any other outer monad as well, like Option[Option[Int]] or String \/ Option[Int]
}