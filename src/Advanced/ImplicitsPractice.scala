package Advanced

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ImplicitsPractice extends App {


  //implicit class -here arrowassoc is implicit class having method def ->
  val pair = "peter" -> 25
  val aTuple = "age" -> 32

  case class Person1(name: String) {
    def greet = s"Welcome $name"
  }

  //implict method
  implicit def stringToPerson(name: String): Person1 = Person1(name)

  println("peter".greet)

  //implicit parameters - Example-1

  def increaseAmount(x: Int)(implicit amount: Int): Int = x + amount

  println(increaseAmount(3))

  implicit val defaultAmount: Int = 10

  println(increaseAmount(3))

  //implicit parameters - Example-2
  val aFuture = Future {
    "peter".greet
  }

  aFuture.onComplete(
    x => x.foreach(println)
  )


  //  ************* Organising Implicits ******************

  implicit def reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)

  //implicit val normalOrdering: Ordering[Int] = Ordering.fromLessThan(_ < _)

  val aList = List(6, 3, 4, 2, 1, 5)
  println(aList.sorted) //aList.sorted(reverseOrdering)

  //implicit parameters can be val/var, object, accessor method(methods without parenthesis)

  //Exercise

  case class Person(name: String, age: Int)

  object Person {
    implicit val personAgePerson: Ordering[Person] = Ordering.fromLessThan((a, b) => a.age < b.age)
  }

  object PersonOrdering {
    implicit val personNameOrdering: Ordering[Person] = Ordering.fromLessThan((p1, p2) => p1.name < p2.name)
  }

  val persons = List(
    Person("Steve", 30),
    Person("Amy", 22),
    Person("John", 66)
  )

  import PersonOrdering._

  println(persons.sorted)


  /*Implicit scope- in order compiler looks for
  1. normal scope or local scope
  2. imports - example -future
  3. companion object of all types in method signature
  */

  ///Exercise
  case class Purchase(nUnit: Int, unitPrice: Double)

  object Purchase {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a, b) => (a.nUnit * a.unitPrice) < (b.nUnit * b.unitPrice))
  }

  object PriceOrdering {
    implicit val unitCountOrdering: Ordering[Purchase] = Ordering.fromLessThan((a, b) => (a.nUnit) < (b.nUnit))
  }

  object UnitOrdering {
    implicit val unitPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a, b) => (a.unitPrice) < (b.unitPrice))
  }

  import PriceOrdering._

  val purchaseList = List(Purchase(1, 2), Purchase(4, 3), Purchase(5, 1.2), Purchase(9, 8), Purchase(2, 2))

  println(purchaseList.sorted)

  //************************TYPE CLASS*****************

  case class User(name: String, age: Int, email: String)

  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean = {
      equalizer.apply(a, b)
    }
  }

  implicit object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  object EmailEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.email == b.email
  }

  val john = User("John", 32, "john@rockthejvm.com")
  //val anotherJohn = User("John", 32, "anotherjohn@rockthejvm.com")
  val marry = User("Marry", 32, "john@rockthejvm.com")
  println(EmailEquality(john, marry))

  println(Equal[User](john, marry)(NameEquality))
  println(Equal[User](john, marry)(EmailEquality))
  println(Equal[User](john, marry))
  println(Equal(john, marry)) //without type User
}
