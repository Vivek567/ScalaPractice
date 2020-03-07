package exercises

object HOFs extends App {

  def toCurry(f: (Int, Int) => Int): Int => Int => Int = {
    x => y => f(x,y)
  }

  def fromCurry(f: Int => Int => Int): (Int, Int) => Int = {
    (x, y) => f(x)(y)
  }

  val sum: (Int, Int) => Int = _ + _
  println(sum(5, 6))
  println(toCurry(sum)(5)(6))
  println(fromCurry(toCurry(sum))(5, 6))

  def compose(f: Int => Int, g: Int => Int) : Int => Int = {
    x => f(g(x))
  }


  def andThen(f: Int => Int, g: Int => Int) : Int => Int = {
     x=> g(f(x))
  }
}