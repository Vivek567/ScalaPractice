package Advanced

object AdvFunProg extends App {

  ///Partial Functions - isDefinedAt, lift, orElse

  val aPartialFunc: PartialFunction[Int, String] = {

    case 1 => "One"
    case 2 => "Two"
    //case _ => "A number"
  }

  println(aPartialFunc(2))
  //println(aPartialFunc(3))

  val liftedFunc = aPartialFunc.lift
  println(liftedFunc(2))
  println(liftedFunc(3))


  //val f: Function1[Int, Int] = v1 => v1 + 1 //SAM : single abstract method

  val anotherPartialFunction: PartialFunction[Int, String] = {
    case 5 => "five"
    case 6 => "Six"
  }

  println(aPartialFunc.orElse(anotherPartialFunction)(5))

  val myPartialFunc: PartialFunction[Int, Int] = new PartialFunction[Int, Int] {
    override def apply(v1: Int): Int = v1 match {
      case 1 => 2
      case 2 => 3
    }

    override def isDefinedAt(x: Int): Boolean = {
      try {
        this.apply(x)
        true
      }
      catch {
        case e: MatchError => false
        case _ => true
      }
    }
  }

  println(myPartialFunc(1))
  println(myPartialFunc.isDefinedAt(2))
  println(myPartialFunc.isDefinedAt(3))

  val dumbChatBot: PartialFunction[String, String] = {
    case name => s"Hi $name, How can i help you"
  }


  val simpleAddFunction = (x: Int, y: Int) => x + y

  def simpleAddMethod(x: Int, y: Int): Int = x + y

  def curriedAddMethod(x: Int)(y: Int) = x + y

  val add7_1: Int => Int = y => simpleAddFunction(7, y)
  val add7_2: Int => Int = y => simpleAddMethod(7, y)
  val add7_3: Int => Int = curriedAddMethod(7)
  val add7_4 = curriedAddMethod(7) _ //Partial applied function
  val add7_5 = (x: Int) => simpleAddFunction(7, x)
  val add7_6 = (x: Int) => simpleAddMethod(7, x)
  val add7_7 = (x: Int) => curriedAddMethod(7)(x)
  val add7_8 = simpleAddFunction.curried(7)
  val add7_9 = curriedAddMethod(7)(_) //PAF alternative syntax of curriedAddMethod(7) _
  val add7_10 = simpleAddFunction(7, _: Int)
  val add7_11 = simpleAddMethod(7, _: Int)

  println(add7_1(2))
  println(add7_2(2))
  println(add7_3(2))
  println(add7_4(2))
  println(add7_5(2))
  println(add7_6(2))
  println(add7_7(2))
  println(add7_8(2))
  println(add7_9(2))
  println(add7_10(2))
  println(add7_11(2))


  //Exercise-1

  def curriedFormatter(format: String)(number: Double): String = format.format(number)

  val curriedDecimalFormatter = curriedFormatter("%4.2f") _
  val curriedPrecisionFormatter = curriedFormatter("%8.6f") _
  val curriedMorePrecisionFormatter = curriedFormatter("%14.12f") _

  val aList = List(3.14, 2.888, 4, 6.5)

  aList.map(curriedDecimalFormatter).foreach(x => print(s"-$x-"))
  println("")
  aList.map(curriedPrecisionFormatter).foreach(x => print(s"-$x-"))
  println("")
  aList.map(curriedMorePrecisionFormatter).foreach(x => print(s"-$x-"))


  //Exercise-2

  def byName(x: => Int): Int = x + 1

  def byFunction(f: () => Int): Int = f() + 1

  def method: Int = 42

  def parenMethod(): Int = 42

  println(byName(method))
  println(byName(parenMethod()))

  //println(byFunction(method))
  println(byFunction(parenMethod))

  val x: Int = {
    println("Hi")
    42
  }
  println(x)
  println(x)
  println(x)

  def aCondition : Boolean = {
    println("Boo")
    true
  }

  def anotherCondition : Boolean =false
  lazy val cond = aCondition
  println(if(anotherCondition && aCondition) "yes" else "no")
  println(if(anotherCondition && cond) "yes" else "no")
}