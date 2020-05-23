package playground

object ScalaPlayground extends App {
  println("Hello Scala")
  val x: Int = 42
  println(x)

  class A(name: String, age: Int) {

    def fun(x: Int): Int = x + 1

    def this() = this("", 0)
  }

  case class B(id: Int, role: Int) extends A{
    override def fun(x: Int): Int = {
      println(id)
      println(role)
      //println(name)
      println()
      super.fun(x) + 2

    }
  }

  println()
}