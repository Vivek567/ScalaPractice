package exerciseadv

import exerciseadv.PatternMatching.MyList

object FunctionalSet extends App {

  trait MySet[A] extends (A => Boolean) {

    def apply(element: A): Boolean = contains(element)

    def contains(element: A): Boolean

    def +(element: A): MySet[A]

    def ++(set: MySet[A]): MySet[A]

    def map[B](f: A => B): MySet[B]

    def flatMap[B](f: A => MySet[B]): MySet[B]

    def filter(predicate: A => Boolean): MySet[A]

    def foreach(f: A => Unit): Unit

    def -(element: A) : MySet[A]

    def --(set:MySet[A]): MySet[A]

    def &(set:MySet[A]):MySet[A]

    def unary_! : MySet[A]
  }

  class PropertyBasedSet[A](property : A=> Boolean) extends MySet[A] {
    override def contains(element: A): Boolean = ???

    override def +(element: A): MySet[A] = ???

    override def ++(set: MySet[A]): MySet[A] = ???

    override def map[B](f: A => B): MySet[B] = ???

    override def flatMap[B](f: A => MySet[B]): MySet[B] = ???

    override def filter(predicate: A => Boolean): MySet[A] = ???

    override def foreach(f: A => Unit): Unit = ???

    override def -(element: A): MySet[A] = ???

    override def --(set: MySet[A]): MySet[A] = ???

    override def &(set: MySet[A]): MySet[A] = ???

    override def unary_! : MySet[A] = ???
  }

  class EmptySet[A] extends MySet[A] {
    override def contains(element: A): Boolean = false

    override def +(element: A): MySet[A] = new NonEmptySet[A](element, this)

    override def ++(set: MySet[A]): MySet[A] = set

    override def map[B](f: A => B): MySet[B] = new EmptySet[B]

    override def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]

    override def filter(predicate: A => Boolean): MySet[A] = new EmptySet[A]

    override def foreach(f: A => Unit): Unit = ()

    override def apply(v1: A): Boolean = false

    def -(element: A): MySet[A]= this //throw new NoSuchElementException

    override def --(set: MySet[A]): MySet[A] = this

    override def &(set: MySet[A]): MySet[A] = this

    def unary_! : MySet[A] = ???
  }

  class NonEmptySet[A](val head: A, val tail: MySet[A]) extends MySet[A] {
    override def contains(element: A): Boolean = {
      head == element || tail.contains(element)
    }

    override def +(element: A): MySet[A] = {
      if (this.contains(element)) this
      else new NonEmptySet[A](element, this)

    }

    override def ++(set: MySet[A]): MySet[A] = (tail ++ set) + head

    override def map[B](f: A => B): MySet[B] = new NonEmptySet[B](f(head), this.tail.map(f))

    override def flatMap[B](f: A => MySet[B]): MySet[B] = f(head) ++ this.tail.flatMap(f)

    override def filter(predicate: A => Boolean): MySet[A] = {
      if (predicate(head)) new NonEmptySet[A](head, this.tail.filter(predicate))
      else this.tail.filter(predicate)
    }

    override def foreach(f: A => Unit): Unit = {
      f(head)
      this.tail.foreach(f)
    }

    override def apply(v1: A): Boolean = this.contains(v1)

    override def -(element: A): MySet[A] = {
      if (head == element) tail
      else tail - element + head
    }

    override def --(set: MySet[A]): MySet[A] = tail -- set - head

    override def &(set: MySet[A]): MySet[A] = {
      val commonTail = set & tail
      if (set.contains(head)) commonTail + head
      else commonTail
    }

    def unary_! : MySet[A] = ???
  }

  object MySet {

    def apply[A](values: A*): MySet[A] = {

      def buildSet(valSeq: Seq[A], acc: MySet[A]): MySet[A] = {
        if (valSeq.isEmpty) acc
        else buildSet(valSeq.tail, acc + valSeq.head)

      }

      buildSet(values.toSeq, new EmptySet[A])
    }
  }

  //val set: MySet[Int] = new NonEmptySet[Int](2, new NonEmptySet[Int](3, new EmptySet[Int]))
  val numbers = MySet(1, 2, 3, 4)
  //numbers.foreach(println)
  //numbers + 5 + 5 foreach println
  val anotherSet = numbers ++ MySet(2, 3, 4, 5, 6)
  //anotherSet foreach println
  //anotherSet flatMap(x=>MySet(x*10)) foreach println
  //anotherSet filter (_%2==0)  foreach println
  //println(set.contains(5))
  //numbers -1  foreach(println)
  numbers & anotherSet foreach(println)
}

