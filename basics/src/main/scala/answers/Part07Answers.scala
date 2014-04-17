package answers

import flatten.Part07
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait Part07Answers extends Part07 {

  case class FinishedFutureOption[A](contents: Future[Option[A]]) {
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
}