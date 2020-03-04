package exercises

abstract class MyList[+A]  {
  def head : A
  def tail : MyList[A]
  def isEmpty : Boolean
  def add[B >: A] (element: B) : MyList[B]
  def printElements : String
  override def toString : String ="[" + printElements + "]"
  def map[B](transformer : MyTransformer[A, B]) : MyList[B]
  def filter(myPredicate : MyPredicate[A]) : MyList[A]
  def ++[B>:A](list: MyList[B]): MyList[B]
  def flatMap[B](transformer : MyTransformer[A, MyList[B]]) : MyList[B]
}

trait MyPredicate[-T] {
  def test(value: T) : Boolean
}

trait MyTransformer[-A, B] {
  def transform(element: A) : B
}

object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException

  def tail: MyList[Nothing] = throw new NoSuchElementException

  def isEmpty: Boolean = true

  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty )

  def printElements : String = " "

  override def map[B](transformer: MyTransformer[Nothing, B]): MyList[Nothing] = Empty

  override def filter(myPredicate: MyPredicate[Nothing]): MyList[Nothing] = Empty

  override def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
  override def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[Nothing] = Empty
}

class Cons[+A] (h: A, t: MyList[A]) extends MyList[A] {
  override def head: A = this.h

  override def tail: MyList[A] = this.t

  override def isEmpty: Boolean = false

  override def add[B >: A](element: B): MyList[B] = new Cons[B](element, this)


  override def printElements(): String = {
    if (t.isEmpty) "" + h
    else h + " " + t.printElements
  }

  override def map[B](transformer: MyTransformer[A, B]): MyList[B] = {
    new Cons[B](transformer.transform(h), t.map(transformer))
  }

  override def filter(myPredicate: MyPredicate[A]): MyList[A] = {
    if (myPredicate.test(h)) new Cons[A](h, t.filter(myPredicate))
    else t.filter(myPredicate)
  }

  override def ++[B >: A](list: MyList[B]): MyList[B] = {
    new Cons[B](h, t ++ list)
  }

  override def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] = {
    transformer.transform(h) ++ t.flatMap(transformer)
  }
}

object ListTest extends App {

  val list = new Cons[Int](1, new Cons(2, new Cons(3, Empty)))
  list.add(4)
  println(list.toString)

  val listOfString = new Cons[String]("Hello", new Cons("Scala", Empty))
  val anotherList = new Cons[Int](6, new Cons(7, Empty))
  println(listOfString.toString)

  println(listOfString.map((a) => a + a).toString)

  println(list.map(new MyTransformer[Int, Int] {
    override def transform(element: Int): Int = {
      element * 2
    }
  }).toString)

  println(list.filter(new MyPredicate[Int] {
    override def test(value: Int): Boolean = value % 2 == 0
  }).toString)

  println(list.flatMap(new MyTransformer[Int, MyList[Int]] {
    override def transform(element: Int): MyList[Int] = new Cons(element, new Cons(element + 1, Empty))
  }).toString)
}