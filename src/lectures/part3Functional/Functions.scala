package lectures.part3Functional
import lectures.lectures.basics.basics._

object Functions extends App {

  val stringConcatinator: (String, String) => String = new Function2[String, String, String] {
    override def apply(str1: String, str2: String): String = str1 + " " + str2
  }

  val fun = new Function0[Int] {
    override def apply(): Int = 42
  }

  println(fun())
  println(stringConcatinator("vivek", "agrawal"))


  val adder: Int => (Int => Int) = x => y => x + y
  println(adder(3)(4))


  //*******HOF and curried fun **********

  def nTimes(f: Int => Int, n: Int, x: Int): Int = {
    if (n <= 0) x
    else nTimes(f, n - 1, f(x))
  }

  val plusOne: (Int => Int) = x => x + 1

  println(nTimes(plusOne, 10, 1))

  def nTimesBetter(f: Int => Int, n: Int): Int => Int = {
    if (n <= 0) (x: Int) => x
    else (x: Int) => nTimesBetter(f, n - 1)(f(x))
  }

  println(nTimesBetter(plusOne, 10)(1))

  def toCurry(f: (Int, Int) => Int): (Int => Int => Int) = x => y => f(x, y)

  def fromCurry(f: Int => Int => Int): (Int, Int) => Int = (x, y) => f(x)(y)

  val numbers = List(1, 2, 3, 4)
  val chars = List('a', 'b', 'c', 'd')
  val colors = List("black", "white")
  val combinations = colors.flatMap(l => chars.flatMap(c => numbers.map(n => c + n.toString + l)))
  println(combinations)

  val aFor= for {
    n <- numbers
    c <- chars
    l <- colors
  } yield (c + n.toString + l)

  val bFor= for {
    l <- colors
    c <- chars
    n <- numbers
  } yield (c + n.toString + l)

  println(aFor)
  println(bFor)

  val aList = new Cons(1,new Cons(2,new Cons(3,Empty)))
  val bList = new Cons('a', new Cons('b', new Cons('c', Empty)))

  val cFor= for {
    a <- aList
    b <- bList
  } yield (a+b)


}
