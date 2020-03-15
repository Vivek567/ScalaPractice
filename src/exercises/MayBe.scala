package exercises

abstract class Maybe[+T] {
  def map[A](transformer: T=> A): Maybe[A]
  def flatMap[A](transformer: T => Maybe[A]) : Maybe[A]
  def filter(predicate: T=>Boolean): Maybe[T]
}

case object MaybeNot extends Maybe[Nothing] {
  override def map[A](transformer: Nothing => A): Maybe[A] = MaybeNot

  override def flatMap[A](transformer: Nothing => Maybe[A]): Maybe[A] = MaybeNot

  override def filter(predicate: Nothing => Boolean): Maybe[Nothing] = MaybeNot
}
case class Just[+T](value: T) extends Maybe[T]{
  override def map[A](transformer: T => A): Maybe[A] = Just(transformer(value))

  override def flatMap[A](transformer: T => Maybe[A]): Maybe[A] = transformer(value)

  override def filter(predicate: T => Boolean): Maybe[T] = {if(predicate(value)) Just(value)
  else MaybeNot
  }
}

object MaybeTest extends App {
  val just3 = Just(3)
  println(just3)
  println(just3.map(_*2))
  println(just3.flatMap(x=> Just(1)))
  println(just3.filter(x=> x%2==0))

  // String is a Seq
  val s: String ="Test"
  println(s.head)
  println(s.tail)

  //array is a mutable seq
  val array = Array.ofDim[Int](3)
  array(1) =4
  println(array.mkString("-|-"))
  val numbers  =Array(1,2,3)
  numbers.foreach(println)
  println(array.tail.mkString("-|-"))

}