package flatten

import scalaz.Scalaz._

trait Part14 extends Part13 {

  // In the previous part, we had constructs like

  // OptionT(Future.successful(theThing)).

  // The `OptionT` and `Future.successful` parts are not so interesting, they're just to make the container right.

  // Scalaz has a function application operator, that reverses function and parameter.

  // This:
  val y1 = double(5)
  // Is equivalent to this:
  val y2 = 5 |> double

  // Exercise, rewrite the for-comprehension from part 13, but use `|>` for applying Future.successful and EitherT.apply


  def double(i: Int) = i * 2
}