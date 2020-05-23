package Advanced

object apm extends App {

  //1. Seal classes - exhaustive pattern matching
  class Animal

  case class Dog(breed: String) extends Animal

  case class Parrot(greet: String) extends Animal

  val animal: Animal = new Dog("K9")
  animal match {
    case Dog(breed) => println(s"Dog of breed $breed")
  }

  //println(doggy)
  //2. Decomposition

  //case class Person(name: String, age: Int)

  class Person(val name: String, val age: Int)

  object Person {
    def unapply(arg: Person): Option[(String, Int)] = Some(arg.name, arg.age)
  }

  val bob = new Person("bob", 32)
  val description = bob match {
    case Person(n, _) => s"my name is $n"
    case _ => s"something"
  }

  println(description)


  val aList = List(1, 2, 3, 4)

  //name binding , var arg pattern

  val nameBindingPattern = aList match {
    case nonEmptyList@List(1, _*) => nonEmptyList.head
    case List(1, rest@_*) => rest.tail
    case _ => 42
  }
  println(nameBindingPattern)

  // multi binding

  val multiBindingPattern = aList match {
    case Nil | List(1, 2, 3, _) => "matched pattern"
    case _ => "Nothing matched"
  }

  println(multiBindingPattern)

  val n: Int = 45

  object CustomIntPattern {
    def unapply(arg: Int): Option[String] =
      if (arg < 10) Some("single digit")
      else if (arg < 100) Some("double digit")
      else if (arg % 2 == 0) Some("even number")
      else if (arg % 2 != 0) Some("odd number")
      else None
  }

  val mathProperty = n match {
    case CustomIntPattern(property) => property
    case _ => "no property"
  }

  println(mathProperty)
}
