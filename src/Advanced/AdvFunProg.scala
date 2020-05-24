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

  val dumbChatBot : PartialFunction[String, String] = {
    case name => s"Hi $name, How can i help you"
  }
}