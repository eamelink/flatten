package answers

import flatten.Part04
import scalaz.Scalaz._

trait Part04Answers extends Part04 {

  // Done with `toRightDisjunction`
  for {
    username <- getUserName(data)
    user <- getUser(username).toRightDisjunction("User not found")
    email = getEmail(user)
    validatedEmail <- validateEmail(email).toRightDisjunction("Invalid e-mail address")
    success <- sendEmail(email)
  } yield success

  // Same thing, but with \/> instead of `toRightDisjunction`
  for {
    username <- getUserName(data)
    user <- getUser(username) \/> "User not found"
    email = getEmail(user)
    validatedEmail <- validateEmail(email) \/> "Invalid e-mail address"
    success <- sendEmail(email)
  } yield success

  // So we can now use three kinds of values in our for-comprehension:
  // - Values inside a \/ with `<-`,
  // - Plain values with `=`
  // - Option values with `<-` and `\/>`.

  // Bonus exercise answer, downgrading \/ to Option:
  for {
    username <- getUserName(data).toOption
    user <- getUser(username)
    email = getEmail(user)
    validatedEmail <- validateEmail(email)
    success <- sendEmail(email).toOption
  } yield success
}