package flatten

trait Part01 {

  // These are the service methods from which we're going to build a program.
  // We'll reuse these five methods in all the parts, although they will evolve a bit.
  def getUserName(data: Map[String, String]): Option[String] = ???
  def getUser(name: String): Option[User] = ???
  def getEmail(user: User): String = ???
  def validateEmail(email: String): Option[String] = ???
  def sendEmail(email: String): Option[Boolean] = ???

  val data = Map[String, String]()

  // The program that we're making is also very similar in all the parts.
  // We're getting a username from a Map with data, then we lookup the corresponding user.
  // We get the email address from the user, validate it, and send an email to it.

  // Not great: nested maps and flatMaps!
  val result1 = getUserName(data).map { username =>
    getUser(username).map { user =>
      val email = getEmail(user)
      validateEmail(email).map { validatedEmail =>
        sendEmail(validatedEmail)
      }
    }
  }

  // Exercise, rewrite the above as a for-comprehension
}

trait User