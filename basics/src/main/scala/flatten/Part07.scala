package flatten

import scalaz.\/
import scalaz.syntax.std.option._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait Part07 {

  // So we're dealing with Future[Option[A]] values. And we want to use them in for-comprehensions.

  // We can't make the for-comprehension desugar differently.

  // So what we need is a `flatMap` operation, that treats these two containers as a single container,
  // and that maps the inner value.

  // Let's make that! We'll create a new datastructure that can get the A value out of a Future[Option[A]],
  // apply a function to it, and put it back.

  // Exercise: Finish FutureOption
  case class FutureOption[A](contents: Future[Option[A]]) {
    def flatMap[B](fn: A => FutureOption[B]): FutureOption[B] = FutureOption {
      contents.flatMap {
        ???
      }
    }

    def map[B](fn: A => B): FutureOption[B] = ???
  }

  // We can use it like this:
  val multiBoxedA = Future(Option(5))
  val multiBoxedB = Future(Option(3))

  // Here, `a` and `b` are Int!
  val result = for {
    a <- FutureOption(multiBoxedA)
    b <- FutureOption(multiBoxedB)
  } yield a + b

  // Then at the end, get the regular structure out of our FutureOption class
  val finalFuture: Future[Option[Int]] = result.contents

}