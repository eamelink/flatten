package flatten

import scalaz.\/

trait Part03 {

  /*

  So Scala's `Either` is pretty useless for what we want. This is caused by
  `Either` being _unbiased_. It equally values the Left and Right sides, so it
  believes that `map` doesn't make since without specifying which side you want
  to map. That's why you need a left or right projection before you can map
  an Either.

  Luckily, Scalaz also has an either-like type! Classname: \/.
  Looks like the mathematical symbol for disjunction, which is the same thing.

  And \/ is right-biased, so it has `map` and `flatMap` methods, and they work
  on the right side.

  So the type Either[String, Int]
  would, using Scalaz, be \/[String, Int]

  But you can use infix notation for the type and write this as: String \/ Int

  The data constructors are \/- and -\/ instead of Right and Left.
  */

  // Here are our service methods again, but now changed to return a Scalaz \/.
  def getUserName(data: Map[String, String]): String \/ String = ???
  def getUser(name: String): String \/ User = ???
  def getEmail(user: User): String = ???
  def validateEmail(email: String): String \/ String = ???
  def sendEmail(email: String): String \/ Boolean = ???

  val data = Map[String, String]()

  // Exercise, write the same program as in Part01 and Part02, with a for-comprehension

}