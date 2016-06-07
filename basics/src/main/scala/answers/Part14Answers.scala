package answers

import flatten.Part14
import scala.concurrent.ExecutionContext.Implicits.global
import scalaz.Scalaz._
import scalaz._
import scala.concurrent.Future

trait Part14Answers extends Part14 {

  for {
    username <- getUserName(data) |> Future.successful |> OptionT.apply
    user <- getUser(username) |> OptionT.apply
    email = getEmail(user)
    validatedEmail <- validateEmail(email) |> Future.successful |> OptionT.apply
    success <- sendEmail(email) |> OptionT.apply
  } yield success

}