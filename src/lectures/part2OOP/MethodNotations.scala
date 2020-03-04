package lectures.part2OOP

object MethodNotations extends App {

  class Person(name: String, favMovie: String, val age : Int = 0)
  {
    def +(nickName: String) : Person = new Person(s"$name ($nickName)", favMovie)
    def unary_+ = new Person(this.name,favMovie,age+1)
    def apply() = println(s"$name likes $favMovie")
  }

  val marry = new Person("Marry", "Inception", 10)
  (marry + "rockstar").apply()
  println( (+marry).age)

}
