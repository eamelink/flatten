package answers

import flatten.Part09

trait Part09Answers extends Part09 {

  trait Serializable[A] {
    def serialize(value: A): Array[Byte]
  }

  def toBytes[A: Serializable](value: A) = implicitly[Serializable[A]].serialize(value)

  implicit val StringSerializable = new Serializable[String] {
    override def serialize(value: String) = value.toString.getBytes
  }

  implicit val IntSerializable = new Serializable[Int] {
    override def serialize(value: Int) = value.toString.getBytes
  }

}