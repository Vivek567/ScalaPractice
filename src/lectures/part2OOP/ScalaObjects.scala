package lectures.part2OOP

object ScalaObjects {

  //*************Scala Objects *************//

  //Scala  does not have class level functionality ( static classes/methods)
  // Scala object are singlenton object

  // Scala object have static/class level functionality where class have instance level functionaly . they are called companions
  // Scala object can never have parameters
  // apply pattern for factory method

  def main(args: Array[String]): Unit = {
    class Person(val name: String)
    object Person {

      def apply(mother: Person, father: Person): Person = new Person("Bobbie")
    }

    val marry = new Person("Marry")
    val john = new Person("John")
    val bob = Person(marry, john)
    println(bob.name)


    //********************** Inheritance **************************

    // Scala support single class inheritance
    class Animal {
      val N_LEGS = 4
      val creatureType = "wild"

      def eat = println("nom nom nom")
    }


    class Cat extends Animal {
      override def eat: Unit = println("chom chom chom")
    }



    val cat = new Cat
    cat.eat

    println(cat.N_LEGS)

    //constructors- while inheriting base class need to specify constructor of base class . jvm calls base class constrtor first then child class

    class Person1(name: String, age: Int) {
      def this(name: String) = this(name, 0)
    }
    class AnotherPerson(name1: String, age1: Int, idCard: String) extends Person1(name1, age1)


    //override field in constructor

    class Dog(override val creatureType: String) extends Animal {
      override def eat: Unit = println("crunch crunch crunch")
    }

    //polymorphism
    val unknownAnimal: Animal = new Dog("K9")
    unknownAnimal.eat

    //Exceptions

    //val aWeiredValue = throw new NullPointerException
    //println(aWeiredValue)

    def devide(x: Int, y: Int): Int = x / y

    try {
      devide(5, 0)
    }
    catch {
      case e: NullPointerException => println("caught a null pointer exception")
      case e: ArithmeticException => println("caught an arithmetic exception")
      case e: RuntimeException => println("caught a run time exception")

    }
    finally {
      println("FINALLY")
    }


    //val arr = Array.ofDim(Int.MaxValue) OutOfMemory Error

    // def infinite: Int = 1 + infinite
    // println(infinite) // Stack overflow error

    class OverFlowException extends Exception
    class PocketCalculator {
      def add(x: Long, y: Long): Long = {
        try {
           if( x + y > Int.MaxValue)
             throw new OverFlowException
          else
             x+y
        }
        catch {
          case e: ArithmeticException => 0
        }
      }
    }

    val pocketCalculator = new PocketCalculator
    println(pocketCalculator.add(Int.MaxValue, 10))
  }
}
