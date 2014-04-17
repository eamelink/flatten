package flatten

import scalaz.\/
import scalaz.syntax.std.option._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait Part05 {

  // There's nothing special about the types you can use in a for-comprehension. The Scala compiler
  // will happily desugar for you, and as long as the type you work with has the proper `map` and `flatMap`
  // methods where required, it will be fine.

  // For for-comprehensions with no `yield`, the last step gets desugared to `foreach`.

  /* And if you use guards, like this:

  for {
    foo <- bar if quux
  }
  */
  // Then a `withFilter` method is required.


  // Future has a `flatMap` and `map` method as well, so can also be used in a `for` comprehension
  val fa = Future(3)
  val fb = Future(5)

  for {
    a <- fa
    b <- fb
  } yield a + b


  // Debugging!

  // Lines in a `for`-comprehension *must* be a generator (<-) or a value definition(=). What if we just want to println an intermediate value?
  for {
    a <- Option(3)
    b <- Option(5)
    _ = println("A = " + a)
    c <- Option(11)
  } yield a + b + c


  // If your yield gets unwieldy, you can also just assign it to a value
  for {
    a <- Option(3)
    b <- Option(5)
    c <- Option(11)
    result = a + b + c
  } yield result



}