package exercises

abstract class MyList[+A] {
  def head: A

  def tail: MyList[A]

  def isEmpty: Boolean

  def add[B >: A](element: B): MyList[B]

  def printElements: String

  override def toString: String = "[" + printElements + "]"

  def map[B](transformer: A => B): MyList[B]

  def filter(myPredicate: A => Boolean): MyList[A]

  def ++[B >: A](list: MyList[B]): MyList[B]

  def flatMap[B](transformer: A => MyList[B]): MyList[B]

  def foreach(f: A => Unit): Unit

  def sort(f: (A, A) => Int): MyList[A]

  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C]

  def fold[B](start: B)(operator: (B, A) => B): B
}


object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException

  def tail: MyList[Nothing] = throw new NoSuchElementException

  def isEmpty: Boolean = true

  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty )

  def printElements : String = " "

  override def map[B](transformer: Nothing => B): MyList[Nothing] = Empty

  override def filter(myPredicate: Nothing =>Boolean): MyList[Nothing] = Empty

  override def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
  override def flatMap[B](transformer: Nothing => MyList[B]): MyList[Nothing] = Empty

  override def foreach (f: Nothing => Unit): Unit = () //println("No element found")
  override def sort(f: (Nothing, Nothing) => Int): MyList[Nothing] = Empty

  override def zipWith[B, C](list: MyList[B], zip: (Nothing, B) => C): MyList[C] = {
    if(!list.isEmpty) throw new RuntimeException("List do not have same length")
    else Empty
  }

  override def fold[B](start: B)( operator: (B, Nothing) => B): B = start
}

class Cons[+A] (h: A, t: MyList[A]) extends MyList[A] {
  override def head: A = this.h

  override def tail: MyList[A] = this.t

  override def isEmpty: Boolean = false

  override def add[B >: A](element: B): MyList[B] = new Cons[B](element, this)


  override def printElements: String = {
    if (t.isEmpty) "" + h
    else h + " " + t.printElements
  }

  override def map[B](transformer: A => B): MyList[B] = {
    new Cons[B](transformer(h), t.map(transformer))
  }

  override def filter(myPredicate: A => Boolean): MyList[A] = {
    if (myPredicate(h)) new Cons[A](h, t.filter(myPredicate))
    else t.filter(myPredicate)
  }

  override def ++[B >: A](list: MyList[B]): MyList[B] = {
    new Cons[B](h, t ++ list)
  }

  override def flatMap[B](transformer: A => MyList[B]): MyList[B] = {
    transformer(h) ++ t.flatMap(transformer)
  }

  override def foreach(f: A => Unit): Unit = {
    f(h)
    if (!t.isEmpty) t.foreach(f)
  }

  override def sort(compare: (A, A) => Int): MyList[A] = {
    def insert(x: A, sortedList : MyList[A]) : MyList[A] = {
      if(sortedList.isEmpty) new Cons[A](x, Empty)
      else if(compare(x, sortedList.head) <= 0) new Cons[A](x, sortedList)
      else new Cons[A](sortedList.head, insert(x, sortedList.tail))
    }

    val sortedTail = t.sort(compare)
    insert(h, sortedTail)
  }

  override def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C] = {
    if(list.isEmpty) throw new RuntimeException("Lists do not have same length")
    new Cons[C](zip(h,list.head), tail.zipWith(list.tail, zip))
  }

  override def fold[B](start: B)( operator: (B, A) => B): B = {
    t.fold(operator(start, h))(operator)
  }
}

object ListTest extends App {

  val list = new Cons[Int](1, new Cons(2, new Cons(3, Empty)))
  list.add(4)
  println(list.toString)
  println(list.sort((x,y) => y-x).toString)
  val listOfString = new Cons[String]("Hello", new Cons("Scala", new Cons("Test", Empty)))
  val anotherList = new Cons[Int](6, new Cons(7, Empty))
  println(listOfString.toString)

  println(listOfString.map(a => a + a).toString)

  println(list.map(
    (x: Int) => x * 2).toString)

  println(list.filter(_ % 2 == 0).toString)

  println(list.flatMap(new Function1[Int, MyList[Int]] {
    override def apply(element: Int): MyList[Int] = new Cons(element, new Cons(element + 1, Empty))
  }).toString)


  println(list.flatMap(element => new Cons(element, new Cons(element + 1, Empty))
  ).toString)

  listOfString.foreach(println)

  println(list.zipWith(listOfString, (a : Int, b : String)  => a + " " + b).toString)

  println(listOfString.fold("V")((a,b) => a+b))
  println(list.fold(5)(_+_))

  val combinations = for {
    n <- list
    s <- listOfString
  } yield n+"-"+s
  println(combinations)
  println(list.flatMap(n=> listOfString.map(s=> n+"-"+s)).toString)
}