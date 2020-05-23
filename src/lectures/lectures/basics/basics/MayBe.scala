package lectures.lectures.basics.basics


abstract class MayBe[+T] {
  def map[B](f: T => B): MayBe[B]

  def flatMap[B](f: T => MayBe[B]): MayBe[B]

  def filter(f: T => Boolean): MayBe[T]

}

object None extends MayBe[Nothing] {
  override def map[B](f: Nothing => B): MayBe[B] = None

  override def flatMap[B](f: Nothing => MayBe[B]): MayBe[B] = None

  override def filter(f: Nothing => Boolean): MayBe[Nothing] = None
}

case class Something[T](aVal: T) extends MayBe[T] {
  override def map[B](f: T => B): MayBe[B] = new Something[B](f(aVal))

  override def flatMap[B](f: T => MayBe[B]): MayBe[B] = f(aVal)

  override def filter(f: T => Boolean): MayBe[T] = if (f(aVal)) new Something[T](aVal) else None
}

object TestMayBe extends App{
  val mayBe = new Something[Int](5)
  println(mayBe.map(n=>n*2))
  println(mayBe.filter(n=>n%2==0))
  println(mayBe.flatMap(n=>new Something[Int](n+1)))
}