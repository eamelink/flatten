package answers

import flatten.Part10

trait Part10Answers extends Part10 {

  val optionMonad = new Monad[Option] {
    override def map[A, B](container: Option[A])(function: A => B) = container.map(function)
    override def flatMap[A, B](container: Option[A])(function: A => Option[B]) = container.flatMap(function)
    override def create[B](value: B) = Some(value)
  }

}