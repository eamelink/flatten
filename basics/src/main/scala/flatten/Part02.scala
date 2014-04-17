package flatten

trait Part02 {

  // In Part 1, our service methods returned `Option`, and we composed a program
  // that returned an `Option` as well.

  // But `Option` is seldomly sufficient, no error reporting!

  // For example, it's impossible to distinquish between a user not being found or
  // the email address failing to validate.

  // So, instead of `Option`, we can use `Either`, which supports a failure value.
  def getUserName(data: Map[String, String]): Either[String, String] = ???
  def getUser(name: String): Either[String, User] = ???
  def getEmail(user: User): String = ???
  def validateEmail(email: String): Either[String, String] = ???
  def sendEmail(email: String): Either[String, Boolean] = ???

  val data = Map[String, String]()

  // Scala Either doesn't have `map` or `flatMap` methods.
  // But you can create a 'right projection', which does have them!
  val result1 = getUserName(data).right.flatMap { username =>
    getUser(username).right.flatMap { user =>
      val email = getEmail(user)
      validateEmail(email).right.flatMap { validatedEmail =>
        sendEmail(validatedEmail)
      }
    }
  }

  // So using right projections, you can use `Either` in a for comprehension:
  for {
    username <- getUserName(data).right
    user <- getUser(username).right
  } yield user


  // However, there is a problem! `RightProjection`s `map` and `flatMap` methods don't
  // return a new `RightProjection`, but an `Either`. And often a for-comprehension will
  // desugar to chained `maps` or `flatMaps`, and that breaks:

  /* For example this:

  for {
    username <- getUserName(data).right
    userNameUpper = username.toUpperCase
  } yield userNameUpper

  */

  // Will desugar to something similar to getUserName(data).right.map{...}.map{...}
  // But after the first `map`, you get an Either, which doesn't have a `map` method, so the second map breaks.

  // So our program that we could change to using for-comprehensions while it consisted of `Option`s, can't be
  // changed to for comprehensions when it uses `Either`.

  /* This doesn't work:

  for {
    username <- getUserName(data).right
    user <- getUser(username).right
    email = getEmail(user)
    validatedEmail <- validateEmail(email).right
    success <- sendEmail(email).right
  } yield success
  */

  // Exercise: Uncomment the failing code to see the compilation errors.
}