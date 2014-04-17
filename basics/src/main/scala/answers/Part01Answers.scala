package answers

import flatten.Part01

trait Part01Answers extends Part01 {

  for {
    username <- getUserName(data)
    user <- getUser(username)
    email = getEmail(user)
    validatedEmail <- validateEmail(email)
    success <- sendEmail(email)
  } yield success

}