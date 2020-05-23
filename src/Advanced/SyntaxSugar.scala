package Advanced
import util.Try

object SyntaxSugar extends App {

  //1. single param functions

  def singleParamFun(x: Int) = x + 1

  val someVal = singleParamFun {
    val y = 0
    println("This is it")
    42
  }

  val aList = List(1, 3, 4, 5)
  val anotherList = aList.map {
    val y = 0
    println("This is it")
    x =>
      (x + 1
        )
  }

  val aTry = Try {

    throw new RuntimeException
  }

  //2. Single abstract method

  trait Action {
    def act(x: Int): Unit
  }

  val aAction: Action = new Action {
    override def act(x: Int): Unit = println(x)
  }
  val action: Action = x => println(x)

  //3. :: and #:: methods

  val bList = 42 :: aList //Last char of method ":" decides right associativity of operator (else left associativity)
  println(bList)
  println(bList :: aList)

  //4. multi word method names

  class TeenGirl(name: String) {
    def `and then said`(gossip: String): Unit = println(s"$name said $gossip")
  }

  val marry = new TeenGirl("Marry")

  marry `and then said` "scala is sweet"

  //5. composite types type1[A,B] <=> A type1 B

  class <[A, B]

  val aVal: Int < Int = new <[Int, Int]

  //6. update and apply

  val aArray = Array(1, 2, 3, 4)
  aArray(2) = 5
  aArray.update(3, 5)
  println(aArray.foreach(println))

  //7. getter setter in mutable types

  class MutableType {
    private var aVal: Int = 34

    def member: Int = aVal

    def member_=(value: Int): Unit = aVal = value

  }

  val mutableType = new MutableType
  mutableType.member = 42 //mutableType.member_=(42)
}
