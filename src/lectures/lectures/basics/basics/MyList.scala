package lectures.lectures.basics.basics

//16-May-2020
abstract class MyList[+A] {
  def head: A

  def tail: MyList[A]

  def isEmpty: Boolean

  def add[B >: A](element: B): MyList[B]

  def map[B](transformer: A => B): MyList[B]

  def flatMap[B](transformer: A => MyList[B]): MyList[B]

  def filter(predicate: A => Boolean): MyList[A]

  def foreach(f: A => Unit): Unit

  def sort(compare: (A, A) => Int): MyList[A]

  def zipWith[B, C](list: MyList[B], f: (A, B) => C): MyList[C]

  def fold[B](start: B)(f: (B, A) => B): B

  def ++[B >: A](list: MyList[B]): MyList[B]

  def printElements: String

  override def toString: String = "[ " + printElements + "]"
}


trait MyTransformer[-A,B] {
  def transform(a: A): B
}

case object Empty extends MyList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyList[Nothing]= throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add[A >: Nothing](element: A): MyList[A] = new Cons[A](element, Empty)

  override def printElements = ""

  override def map[B](transformer: Nothing  => B): MyList[B] = Empty

  override def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = Empty

  override def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty

  override  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list

  override def foreach(f: Nothing => Unit): Unit = ()

  override def sort(compare: (Nothing, Nothing) => Int): MyList[Nothing] = Empty

  override def zipWith[B, C](list: MyList[B], f: (Nothing, B) => C): MyList[C] = Empty

  override def fold[B](start: B)(f: (B, Nothing) => B): B = start
}

case class Cons[+A](val h: A, val t: MyList[A]) extends MyList[A] {
  override def head: A = h

  override def tail: MyList[A] = t

  override def isEmpty: Boolean = false

  override def add[B >: A](element: B): MyList[B] = new Cons(element, this)

  override def printElements: String = {
    if (t.isEmpty) h + " "
    else h + " " + t.printElements
  }

  override def map[B](transformer: A => B): MyList[B] = new Cons[B](transformer(h), t.map(transformer))

  override def flatMap[B](transformer: A => MyList[B]): MyList[B] = transformer(h) ++ t.flatMap(transformer)

  override def filter(predicate: A => Boolean): MyList[A] = if (predicate(h)) new Cons[A](h, t.filter(predicate)) else t.filter(predicate)

  override def ++[B >: A](list: MyList[B]): MyList[B] = new Cons[B](h, t ++ list)

  override def foreach(f: A => Unit): Unit = {
    f(h); t.foreach(f)
  }

  override def sort(compare: (A, A) => Int): MyList[A] = {
    def insert(x: A, sortedList: MyList[A]): MyList[A] = {
      if (sortedList.isEmpty) new Cons[A](x, Empty)
      else if (compare(x, sortedList.head) <= 0) new Cons[A](x, sortedList)
      else new Cons[A](sortedList.head, insert(x, sortedList.tail))
    }

    val sortedTail = t.sort(compare)
    insert(h, sortedTail)
  }

  override def zipWith[B, C](list: MyList[B], f: (A, B) => C): MyList[C] = {
    if (list.isEmpty) Empty
    else new Cons(f(h, list.head), t.zipWith(list.tail, f))
  }

  override def fold[B](start: B)(f: (B, A) => B): B = {
      println(f(start, h))
      t.fold(f(start, h))(f)
  }
}


object TestMyList extends App {
  val list = new Cons(1, new Cons(2, new Cons(3, Empty)))
  println(list.tail.head)
  println(list.toString)

  val listOfStrings = new Cons[String]("Marry", new Cons[String]("Bob", new Cons[String]("Linda", new Cons("Jamin", Empty))))
  println(listOfStrings.add(3).toString)

  val listOfIntegers = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, new Cons[Int](4, Empty))))
  val cloneListOfIntegers = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, new Cons[Int](4, Empty))))
  println(listOfIntegers == cloneListOfIntegers)
  println(listOfIntegers.add(4).toString)
  println(listOfIntegers.add("James").toString)

  println(listOfIntegers.map(n => n * 2).toString)
  println(listOfIntegers.flatMap(a => new Cons[Int](a + 1, new Cons[Int](a + 2, Empty))).toString)

  println(listOfIntegers.filter(n => n % 2 == 0).toString)
  listOfIntegers.foreach(println)
  listOfStrings.foreach(println)

  def f : (String, Int) => String = _+_
  listOfStrings.zipWith(listOfIntegers, f).foreach(println)
  listOfStrings.zipWith[Int, String](listOfIntegers, _ + _).foreach(println)
  listOfIntegers.zipWith(listOfIntegers, (x: Int,y: Int) => x*y).foreach(println)
  listOfIntegers.zipWith[Int, Int](listOfIntegers, _*_).foreach(println)

  println(listOfIntegers.fold[String]("--")(_+ " " +_))
}
