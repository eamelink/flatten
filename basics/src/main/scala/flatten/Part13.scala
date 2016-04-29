package flatten

import scalaz.OptionT
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scalaz.std.scalaFuture.futureInstance

/*
 * If you skipped parts 8 - 12, welcome back!
 */
trait Part13 {

  // In parts 8 to 12, we've derived a _Monad Transformer_, a class that transforms two containers
  // into a single container, that can be conveniently used in a `for-comprehension`.

  // Scalaz has a bunch of monad transformers as well. With two nested containers, like Future[Option[A]],
  // you need the monad transformer that corresponds with the inner container, in this case the Option Transformer,
  // which is called OptionT in Scalaz.

  // A small example:
  val fa: Future[Option[Int]] = ???
  val fb: Future[Option[Int]] = ???

  // Here, a and b are Int, extracted from both the Future and the Option!
  val finalOptionT = for {
    a <- OptionT(fa)
    b <- OptionT(fb)
  } yield a + b

  // And to get back to the normal structure:
  val finalFutureOption: Future[Option[Int]] = finalOptionT.run



  // Back to our good old service methods, where some are now asynchronous: returning a Future.
  def getUserName(data: Map[String, String]): Option[String] = ???
  def getUser(name: String): Future[Option[User]] = ???
  def getEmail(user: User): String = ???
  def validateEmail(email: String): Option[String] = ???
  def sendEmail(email: String): Future[Option[Boolean]] = ???
  val data: Map[String, String] = ???

  // Again, we want to use them in a for comprehension.

  // Now we need to do two things:
  // 1) Upgrade the `Option[A]` values to `Option[Future[A]]`, so they're all the same container type
  // 2) Put all `Option[Future[A]]` into an Option Transformer, OptionT.

  // Exercise: Make a for-comprehension
}
