package answers

import flatten.Part03

trait Part03Answers extends Part03 {

  // Now we can get rid of the `.right` that we had on Scala's Either,
  // because \/ is right-biased.
  for {
    username <- getUserName(data)
    user <- getUser(username)
    email = getEmail(user)
    validatedEmail <- validateEmail(email)
    success <- sendEmail(email)
  } yield success

}