package flatten

import scalaz.\/
import scalaz.syntax.std.option._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait Part06 {
  trait User

  // Let's suppose that our API is partially asynchronous
  def getUserName(data: Map[String, String]): Option[String] = ???
  def getUser(name: String): Future[Option[User]] = ???
  def getEmail(user: User): String = ???
  def validateEmail(email: String): Option[String] = ???
  def sendEmail(email: String): Future[Option[Boolean]] = ???

  val data = Map[String, String]()

  // This causes a problem, the for comprehension desugars to `flatMap`, but we only `flatMap`
  // the outer value. We can't get to the inner value!

  // Simplified, given these methods:
  val fa: Future[Option[Int]] = ???
  val fb: Future[Option[Int]] = ???

  /* The problem is that `a` and `b` are Option[Int], and not Int!
  for {
    a <- fa
    b <- fb
  } yield a - b
  */
}