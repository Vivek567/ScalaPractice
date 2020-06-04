package Advanced

object TypeClasses extends App {

  case class User(name: String, age: Int, email: String)

  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean = {
      equalizer.apply(a, b)
    }
  }

  object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  implicit object EmailEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.email == b.email
  }

  implicit class EqualityEnrichment[T](value: T) {
    def ===(anotherValue: T)(implicit equal: Equal[T]): Boolean = equal(value, anotherValue)

    def ==!(anotherValue: T)(implicit equal: Equal[T]): Boolean = !equal(value, anotherValue)
  }

  val john = User("John", 32, "john@rockthejvm.com")
  //val anotherJohn = User("John", 32, "anotherjohn@rockthejvm.com")
  val marry = User("Marry", 32, "john@rockthejvm.com")
  println(EmailEquality(john, marry))
//implicitly(equal[User])
  println(marry === john)

}
