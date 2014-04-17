package answers

import flatten.Part13
import scalaz.OptionT
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scalaz.contrib.std.futureInstance

trait Part13Answers extends Part13 {

  for {
    username <- OptionT(Future.successful(getUserName(data)))
    user <- OptionT(getUser(username))
    email = getEmail(user)
    validatedEmail <- OptionT(Future.successful(validateEmail(email)))
    success <- OptionT(sendEmail(email))
  } yield success

}