package Advanced

import sun.nio.cs.Surrogate.Generator

import scala.annotation.tailrec

abstract class MyStream[+A] {
  def isEmpty: Boolean

  def head: A

  def tail: MyStream[A]

  def #::[B >: A](element: B): MyStream[B]

  def ++[B >: A](stream: => MyStream[B]): MyStream[B]

  def foreach(f: A => Unit): Unit

  def map[B](f: A => B): MyStream[B]

  def flatMap[B](f: A => MyStream[B]): MyStream[B]

  def filter(predicate: A => Boolean): MyStream[A]

  def take(n: Int): MyStream[A]

  def takeAsList(n: Int): List[A]

}

class EmptyStream extends MyStream[Nothing] {
  override def isEmpty: Boolean = true

  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyStream[Nothing] = throw new NoSuchElementException

  override def #::[B >: Nothing](element: B): MyStream[B] = new NonEmptyStream[B](element, this)

  override def ++[B >: Nothing](stream: => MyStream[B]): MyStream[B] = stream

  override def foreach(f: Nothing => Unit): Unit = ()

  override def map[B](f: Nothing => B): MyStream[B] = this

  override def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = this

  override def filter(predicate: Nothing => Boolean): MyStream[Nothing] = this

  override def take(n: Int): MyStream[Nothing] = this

  override def takeAsList(n: Int): List[Nothing] = Nil
}

class NonEmptyStream[+A](h: A, t: => MyStream[A]) extends MyStream[A] {
  override def isEmpty: Boolean = false

  override val head: A = h

  override lazy val tail: MyStream[A] = t

  override def #::[B >: A](element: B): MyStream[B] = new NonEmptyStream[B](element, this)

  override def ++[B >: A](stream: => MyStream[B]): MyStream[B] = new NonEmptyStream[B](head, tail ++ stream) //t ++ (h #:: stream)

  override def foreach(f: A => Unit): Unit = {
    f(h)
    t.foreach(f)
  }

  override def map[B](f: A => B): MyStream[B] = new NonEmptyStream[B](f(head), tail.map(f))

  override def flatMap[B](f: A => MyStream[B]): MyStream[B] = f(head) ++ tail.flatMap(f)

  override def filter(predicate: A => Boolean): MyStream[A] = if (predicate(h)) new NonEmptyStream[A](h, t.filter(predicate)) else t.filter(predicate)

 /* override def take(n: Int): MyStream[A] = {
    @tailrec
    def takeAux(n: Int, acc: MyStream[A], s: MyStream[A]): MyStream[A] = {
      if (n <= 0) acc
      else takeAux(n - 1, new NonEmptyStream[A](s.head, acc), s.tail)
    }

    takeAux(n, new EmptyStream, this)
  }
*/

  override def take(n: Int): MyStream[A] = {
    if (n <= 0) new EmptyStream
    else if (n == 1) new NonEmptyStream[A](head, new EmptyStream)
    else new NonEmptyStream[A](head, tail.take(n - 1))
  }


  override def takeAsList(n: Int): List[A] = {
    @tailrec
    def takeAux(n: Int, acc: List[A], s: MyStream[A]): List[A] = {
      if (n <= 0) acc
      else takeAux(n - 1, acc :+ s.head, s.tail)
    }

    takeAux(n, Nil, this)
  }
}

object MyStream {
  def from[A](start: A)(generator: A => A): MyStream[A] = {
    new NonEmptyStream[A](start, from(generator(start))(generator))
  }

  def fibb(n: Int): MyStream[Int] = {
    def fib(last: Int, nextLast: Int, n: Int): MyStream[Int] = {
      if (n < 3) new NonEmptyStream[Int](last, new NonEmptyStream[Int](nextLast, new EmptyStream))
      else new NonEmptyStream[Int](last, fib(nextLast, last+nextLast, n - 1))
    }

    fib(1, 1, n)
  }
}

object  TestMyStream extends App {

  val aStream = MyStream.from[Int](1)(x => x + 1)
  println(aStream.head)
  println(aStream.tail.head)
  println(aStream.tail.tail.head)
  println(aStream.takeAsList(100))
  println(aStream.map(_ * 3).take(10).foreach(x => print(s"-$x-")))
  println((5 #:: aStream.map(_ * 3).take(5)).foreach(x => print(s"-$x-")))
  println(aStream.flatMap(x => new NonEmptyStream[Int](x, new NonEmptyStream[Int](x + 1, new EmptyStream))).take(50).foreach(x => print(s"-$x-")))
  println(aStream.filter(x=> x<10).take(9).foreach(x => print(s"-$x-")))

  val fibonacci= MyStream.fibb(7).take(7).foreach(x => print(s"-$x-"))
}
