package exerciseadv

object PatternMatching extends App {

  object even {
    def unapply(arg: Int): Option[Boolean] = if (arg % 2 == 0) Some(true) else None
  }

  object singleDigit {
    def unapply(arg: Int): Option[Boolean] = if (arg < 10) Some(true) else None
  }

  val n: Int = 46
  val mathProperty = n match {
    case singleDigit(_) => s"Single digit number"
    case even(_) => s"an even number"
    case _ => "no property"
  }

  println(mathProperty)

  // infix patterns

  case class Or[A, B](a: A, b: B)

  val either = Or(2, "Two")
  val numberAsString = either match {
    case number Or str => s"$number is written as $str"
    case _ => "wildcard"
  }

  println(numberAsString)

  //decomposing sequence
  abstract class MyList[+A] {
    def head: A = ???

    def tail: MyList[A] = ???
  }

  case object Empty extends MyList[Nothing]

  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  case object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] = {
      if (list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
    }
  }

  val listSeq = new Cons[Int](1, Cons(2, Cons(3, Empty)))
  val pattern = listSeq match {
    case MyList(1,2, _*) => "starting with 1 and 2"
    case _ => "something else"
  }

  println(pattern)
}
