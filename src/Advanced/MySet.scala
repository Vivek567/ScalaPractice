package Advanced

import scala.annotation.tailrec

//import exerciseadv.FunctionalSet.MySet

trait MySet[A] extends (A => Boolean) {

  def contains(element: A): Boolean

  def +(element: A): MySet[A]

  def ++(anotherSet: MySet[A]): MySet[A]

  def map[B](f: A => B): MySet[B]

  def flatMap[B](f: A => MySet[B]): MySet[B]

  def filter(predicate: A => Boolean): MySet[A]

  def foreach(f: A => Unit): Unit

  def -(element: A): MySet[A]

  //intersection
  def &(anotherSet: MySet[A]) : MySet[A]

  //difference
  def --(anotherSet: MySet[A]) : MySet[A]

  def unary_! : MySet[A]

}

 class EmptySet[A] extends MySet[A] {
   override def contains(element: A): Boolean = false

   override def +(element: A): MySet[A] = new NonEmptySet[A](element, this)

   override def ++(anotherSet: MySet[A]): MySet[A] = anotherSet

   override def map[B](f: A => B): MySet[B] = new EmptySet[B]

   override def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]

   override def filter(predicate: A => Boolean): MySet[A] = this

   override def foreach(f: A => Unit): Unit = ()

   override def apply(v1: A): Boolean = contains(v1)

   override def -(element: A): MySet[A] = this

   override def &(anotherSet: MySet[A]) : MySet[A]= this

   override def --(anotherSet: MySet[A]) : MySet[A] = this

   override  def unary_! : MySet[A] = new PropertyBasedSet[A](_!=true)
 }

class PropertyBasedSet[A](property: A=> Boolean) extends MySet[A] {
  override def contains(element: A): Boolean = property(element)

  override def +(element: A): MySet[A] = new PropertyBasedSet[A](x => property(x) || x == element)

  override def ++(anotherSet: MySet[A]): MySet[A] = new PropertyBasedSet[A](x => property(x) || anotherSet(x))

  override def map[B](f: A => B): MySet[B] = politelyFail

  override def flatMap[B](f: A => MySet[B]): MySet[B] = politelyFail

  override def filter(predicate: A => Boolean): MySet[A] = new PropertyBasedSet[A](x => property(x) && predicate(x))

  override def foreach(f: A => Unit): Unit = politelyFail

  override def -(element: A): MySet[A] = filter(x => x != element)

  override def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)

  override def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)

  override def unary_! : MySet[A] = new PropertyBasedSet[A](x => !property(x))

  override def apply(v1: A): Boolean = ???

  def politelyFail = throw new IllegalArgumentException("deep rabbit hole")
}

case class NonEmptySet[A](head:A, tail: MySet[A]) extends MySet[A] {
  override def apply(v1: A): Boolean = contains(v1)

  override def contains(element: A): Boolean = if (head == element) true else tail.contains(element)

  override def +(element: A): MySet[A] = if (this contains element) this else new NonEmptySet[A](element, this)

  override def ++(anotherSet: MySet[A]): MySet[A] = tail.++(anotherSet) + head

  override def map[B](f: A => B): MySet[B] = new NonEmptySet[B](f(head), tail.map(f))

  override def flatMap[B](f: A => MySet[B]): MySet[B] = f(head) ++ tail.flatMap(f)

  override def filter(predicate: A => Boolean): MySet[A] = if (predicate(head)) new NonEmptySet[A](head, tail.filter(predicate)) else tail.filter(predicate)

  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }

  override def -(element: A): MySet[A] = if (head == element) tail else (tail - element) + head

  //intersection
  override def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet) //if(anotherSet(head)) tail.&(anotherSet) + head else tail.&(anotherSet)

  override def --(anotherSet: MySet[A]): MySet[A] = filter(x => !anotherSet(x)) //if(anotherSet(head)) tail.--(anotherSet)  else tail.--(anotherSet) + head

  override  def unary_! : MySet[A] = new PropertyBasedSet[A](x=> !this.contains(x))
}

object MySet {
  def apply[A](values: A*): MySet[A] = {
    @tailrec
    def buildSeq(valSeq: Seq[A], acc: MySet[A]): MySet[A] = {
      if (valSeq.isEmpty) acc
      else buildSeq(valSeq.tail, acc + valSeq.head)

    }

    buildSeq(values.toSeq, new EmptySet[A])
  }
}

object TestMySet extends App {

  val set = new NonEmptySet[Int](1, new NonEmptySet[Int](2, new NonEmptySet[Int](3, new EmptySet)))
  println(set(2))
  println(set(22))
  (set + 2 + 3 + 4 + 6).foreach(println)
  set.map(_ * 2).foreach(println)
  set.flatMap(x => new NonEmptySet[Int](x, new NonEmptySet[Int](x + 1, new EmptySet[Int]))).foreach(println)

  val bigSet = set ++ new NonEmptySet[Int](3, new NonEmptySet[Int](5, new NonEmptySet[Int](6, new EmptySet)))
  bigSet.foreach(println)
  bigSet.filter(_ % 2 == 0).foreach(println)
  println(bigSet(5))


  val setObj = MySet(1,7,3,4,5,6)

  println(setObj(3))
 (setObj++set).foreach(x=> print(s"-$x-"))
  println("")
  (setObj - 7).foreach(x=> print(s"-$x-"))
  println("")
  (setObj & set).foreach(x=> print(s"-$x-"))
  println("")
  (setObj --  set).foreach(x=> print(s"-$x-"))
}