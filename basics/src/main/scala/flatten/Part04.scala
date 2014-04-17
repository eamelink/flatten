package flatten

import scalaz.{ \/, \/- }
import scalaz.syntax.std.option._

trait Part04 {
  trait User

  // In practice, not all of your functions will always return the same type.
  // Here, `getUser` and `validateEmail` now return `Option` instead of `\/`
  def getUserName(data: Map[String, String]): \/[String, String] = ???
  def getUser(name: String): Option[User] = ???
  def getEmail(user: User): String = ???
  def validateEmail(email: String): Option[String] = ???
  def sendEmail(email: String): \/[String, Boolean] = ???

  val data = Map[String, String]()

  // In a `for`-comprehension, we need all the containers to be the same.
  // So we must think about a suitable container that can express everything that
  // we need to express.

  // In this case, that would probably be \/. We can upgrade `Option` to `\/` by
  // specifying a left side, for when the Option is a None.

  // This is done with the method `toRightDisjunction`:

  Some(5).toRightDisjunction("Left side!") // == \/-(5)
  None.toRightDisjunction("Left side!")    // == -\/("Left side!")

  // There's a symbolic method for this as well: \/>
  Some(5) \/> "Left side!" // == \/-(5)

  // Exercise, write our usual program with a for-comprehension, using 'toRightDisjunction' or '\/>'


  // If you're entirely not interested in error messages, you can also decide to
  // 'downgrade' the \/ values to Option. There's a 'toOption' method on \/ for that.

  // Bonus exercise: write the program again, but now downgrading \/ to Option.

}