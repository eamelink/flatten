package flatten

/*
 *
 *
 *
 *
 *
 *
 *
 *
 * Parts 8 - 12 are optional (but recommended!). If you're not interested in generalizing the FutureOption
 * from part 7, you can skip these parts and continue with Part 13!
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
trait Part08 {
  // Now we go off the path a bit, and take a scenic route along type classes.

  // We'll look at various way to do polymorphism. Our example is that we want to serialize
  // objects to bytes.


  // Subtype polymorphism: all types that must be serialized extend a common trait.
  object SubtypePolymorphism {

    trait Serializable {
      def bytes: Array[Byte]
    }

    def toBytes(value: Serializable) = value.bytes
  }
  // Often impractical, because all classes must extend Serializable. What if we want to serialize `String` or `Int`???



  // An alternative is ad-hoc polymorphism
  // Ad-hoc polymorphism is also known as function overloading:
  object AdHocPolymorphism {
    def toBytes(value: String): Array[Byte] = value.getBytes
    def toBytes(value: Int): Array[Byte] = value.toString.getBytes
  }
  // Also impractical, because now our serialization library must know about all possible types we want to serialize. What about the custom types in our app?



  // Solution: glue objects: an object that knows how to serialize a single type.
  // We can create these for all types we want to serialize, without needing to change those types.
  // Also, the library that accepts objects-that-can-be-serialized doesn't need to know about all these types in advance
  object GlueObjects {
    trait Serializable[A] {
      def serialize[A](value: A): Array[Byte]
    }

    def toBytes[A](value: A, serializer: Serializable[A]): Array[Byte] = serializer.serialize(value)

    val StringSerializable = new Serializable[String] {
      override def serialize[String](value: String) = value.toString.getBytes
    }

    val IntSerializable = new Serializable[Int] {
      override def serialize[Int](value: Int) = value.toString.getBytes
    }

    // Using this:
    val myString = "Bananaphone!"
    val myStringBytes = toBytes(myString, StringSerializable)
  }
  // This works fine, but it's often a bit awkward to use with these glue objects.


  // In scala, this can be made nicer by making the glue object implicit:
  object GlueObjects2 {
    trait Serializable[A] {
      def serialize(value: A): Array[Byte]
    }

    def toBytes[A](value: A)(implicit serializer: Serializable[A]) = serializer.serialize(value)

    // Or using a `Context Bound`, which is syntactic sugar for the one above
    def toBytes2[A : Serializable](value: A) = implicitly[Serializable[A]].serialize(value)

    implicit val StringSerializable = new Serializable[String] {
      override def serialize(value: String) = value.getBytes
    }

    implicit val IntSerializable = new Serializable[Int] {
      override def serialize(value: Int) = value.toString.getBytes
    }

    // Using this:
    val myString = "Bananaphone!"
    val myStringBytes = toBytes(myString)

  }
  // This eliminates syntactic overhead.

  // This pattern is called 'type classes'. Our trait `Serializable[A]` is called the type class.
  // The implemented glue objects `StringSerializable` and `IntSerializable` are called 'type class instances'.

}