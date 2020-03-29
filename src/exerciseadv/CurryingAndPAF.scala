package exerciseadv

object CurryingAndPAF extends App {

  def supperAdder: Int => Int => Int = x => y => x + y

  val add3 = supperAdder(3)
  println(add3(5))
  println(supperAdder(3)(5))

  def curriedAdder(x: Int)(y: Int) = x + y

  val add4: Int => Int = curriedAdder(4)
  println(add4(5))
  val add5 = curriedAdder(5) _ //forces compiler to do ETA-expansion === add5 : Int=>Int
  println(add5(5))
  val list = List(1.5,3.14,6.123)
  def curriedFormatter(f: String)(x: Double) : String = f.format(x)
  val simpleFormatter = curriedFormatter("%4.2f")_
  println(list.map(simpleFormatter))
  println(list.map(curriedFormatter("%14.12f")))
  println(list.map(curriedFormatter("%6.4f")))
}
