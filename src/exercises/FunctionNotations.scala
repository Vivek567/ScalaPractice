package exercises

object FunctionNotations extends App {

  class Person(val name: String, val age: Int, favMoview: String) {
    def +(message: String): String = s"$name ($message)"

    def unary_+ : Person = new Person(name, this.age + 1, favMoview)
    def learns(thing: String) : String = s"$name learns $thing"

    def apply(n: Int): String = s"$name watched $favMoview $n times."
  }

  val marry = new Person("Mary", 34, "Inception")
  println(marry + "The rockstar")
  println((+marry).age)

  println (marry learns "scala")

  println(marry(3))
}
