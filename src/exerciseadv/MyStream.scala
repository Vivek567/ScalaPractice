package exerciseadv

import scala.annotation.tailrec

abstract class MyStream[+A] {
  def isEmpty: Boolean

  def head: A

  def tail: MyStream[A]

  def #::[B >: A](element: B): MyStream[B]

  def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B]

  def foreach(f: A => Unit): Unit

  def map[B](f: A => B): MyStream[B]

  def flatMap[B](f: A => MyStream[B]): MyStream[B]

  def filter(predicate: A => Boolean): MyStream[A]

  def take(n: Int): MyStream[A]

  def takeAsList(n: Int): List[A] = take(n).toList()

  @tailrec
  final def toList[B >: A](acc: List[B] = Nil): List[B] = {
    if (isEmpty) acc.reverse
    else tail.toList(head :: acc)
  }
}

object MyStream {
  def from[A](start: A)(generator: A => A): MyStream[A] = {
    new Cons[A](start, from(generator(start))(generator))
  }
}
object Empty extends MyStream[Nothing] {
  override def isEmpty: Boolean = true

  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyStream[Nothing] = throw new NoSuchElementException

  override def #::[B >: Nothing](element: B): MyStream[B] = new Cons[B](element, this)

  override def ++[B >: Nothing](anotherStream: => MyStream[B]): MyStream[B] = anotherStream

  override def foreach(f: Nothing => Unit): Unit = ()

  override def map[B](f: Nothing => B): MyStream[B] = this

  override def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = this

  override def filter(predicate: Nothing => Boolean): MyStream[Nothing] = this

  override def take(n: Int): MyStream[Nothing] = this
}
class Cons[A](hd: A, tl : => MyStream[A]) extends  MyStream[A] {
  override def isEmpty: Boolean = false

  override val head: A = hd

  override lazy val tail: MyStream[A] = tl

  override def #::[B >: A](element: B): MyStream[B] = {
    new Cons[B](element, this)
  }

  override def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] = {
    new Cons[B](head, tail ++ anotherStream)
  }

  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }

  override def map[B](f: A => B): MyStream[B] = {
    new Cons[B](f(head), tail.map(f))
  }

  override def flatMap[B](f: A => MyStream[B]): MyStream[B] = {
    f(head) ++ tail.flatMap(f)
  }

  override def filter(predicate: A => Boolean): MyStream[A] = {
    if (predicate(head)) new Cons[A](head, tail.filter(predicate))
    else tail.filter(predicate)
  }

  override def take(n: Int): MyStream[A] = {
    if (n <= 0) Empty
    else new Cons[A](head, tail.take(n - 1))
  }
}
object MyStreamPlayGround extends App {

  println("My Stream")

  val naturals = MyStream.from(1)(x => x + 1)
  println(naturals.head)
  println(naturals.tail)
  println(naturals.tail.head)
  println(naturals.tail.tail.tail.head)

  val startFromZero = 0 #:: naturals
  println(startFromZero.head)

  //startFromZero.take(1000).foreach(println)
  //startFromZero.map(_*2).take(1000).foreach(println)
  //startFromZero.flatMap(x => new Cons[Int](x, new Cons(x+1, Empty))).take(10).foreach(println)
  //println(startFromZero.filter(_ < 10).take(10).take(20).toList())

  println("fibonacci")
  def fibonacci(n: Int): Int = {
    @tailrec
    def fibAux(x: Int, acc: Int, acc1: Int): Int = {
      if (x <= 0) acc
      else fibAux(x - 1, acc + acc1, acc)
    }

    fibAux(n, 1, 1)
  }
  val fibStream = MyStream.from(1)(fibonacci)

  fibStream.take(10).foreach(println)
}

