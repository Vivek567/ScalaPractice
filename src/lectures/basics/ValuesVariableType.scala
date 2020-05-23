package lectures.basics

object ValuesVariableType extends App {

  //1. Val is immutable
  val x: Int = 42
  // x=43 Error= reassigment to val

  //2. Compiler can infer type
  val y = 34 // y is int by default

  //val z : Int = "Hello" //Error: Type mismatch

  // 3. semicolon can be omitted when expression/statement is written in next line. But needed when in same line
  val a: Int = 23;
  val b1 = "Hi"

  //4. Built in types

  val b: Boolean = true
  val aChar: Char = 'a'
  val i: Int = 42 //32-byte
  val s: Short = 2342 //16-byte
  val l: Long = 12312412441L //Without L suffix, it will give complie time error (Integer out of range) - 64 byte
  val fl: Float = 2.0123123f //Without f suffix, it will give complie time error (expression doesn't confirm the expected type float)
  val db: Double = 3.14
  val st: String = "Hello Scala"

  println(Int.MinValue);
  println(Int.MaxValue)
  println(Short.MinValue);
  println(Short.MaxValue)
  println(Long.MinValue);
  println(Long.MaxValue)
  println(Float.MinValue);
  println(Float.MaxValue)
  println(Double.MinValue);
  println(Double.MaxValue)

  //5. Side effects
  var aVar = 5
  aVar = 6 //side effect
  ///************Expressions**********************

  println("************Expressions**********************")

  //1. Expressions are evaluated
  println(1 + 3 * 6)
  //2. ArithMatic Operators + _ * / Modulous %, Exponent **

  // 3. Relational Operators < > == >= <= !=
  // 4. Logical Operator ! && ||
  // 5. Assignment operators = , +=, -+, *=, /=, %=, >>= <<= >>>=
  // 6. Bitwise operators ! $ | >> << >>> ~

  println(16 >> 2) // Right shift
  println(32 >> 2) //(shift right by 2 places)( 32 bit integer) 000000000000-1-0-0-0-0-0 => 2^5  here. 2^5>>2   => 0-0-1-0-0-0=> 2^3 =8
  println(16 >>> 2)
  println(2 << 32) // Left Shift (shift left by 32 places) 0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-1-0 = 2^5 =32 here, 2^5 <<2  => 0-0-0-0-1-0 => 2^1 =2
  println(2 << 16) // Left Shift((shift left by 32 places)) 2^17
  println(2147483647 >>> 16)
  println(2147483607 >>> 16)
  println(2147483607 >> 16)
  println(~2) //The complement operator (~) JUST FLIPS BITS. It is up to the machine to interpret these bits.
  //0-0-0-0-0-0-1-0  1-1-1-1-1-1-0-1

  //3. IF Expression

  val IfExpression = if (2 > 3) 5 else 6 //type Int
  println(IfExpression)

  val ifExpression1 = if (2 < 3) 5 else "Hi" // Any type
  println(ifExpression1)

  //4. While loop is side effets and type of unit
  val aWhile = {
    var i = 0
    while (i < 5)
      i += 1
  }

  println(aWhile)

  //5. Unit type is equls to void in other lang. its only value is ()
  //6. Instructions are excuted but expression are evaluated


  ///***************** Functions*************

  //1. How compiler treats code block and parameterless functions?
  val aCodeBlock = {
    if (3 > 4) 5 else 6
    42
  }

  def aPrameterlessFunction = {
    if (3 > 4) 5 else 6
    42
  }


  println(aCodeBlock)
  println(aPrameterlessFunction)

  //2. Recusrive function must have return type specified esle compiler will give error

  //*********Type Interference **************

  val aInt = 42
  val aType = x + "items" //string


  //**********/stack and tail recursion*****
  //Stack recursion gives stack overflow error when large number of intermediate results are stored in stack frames to be evaluated
  //Where in tail recursion evalutaion logic in passed as last parmeter , in this case compiler don't need additional stack frames

  //CallbyValyendCallByName
  //Callby Value- parameter value is replace in parameters of function definition- evaluated before function call
  //CallbyName- parameter name is replace in function definition. Evaluated when function is call or being executed.
  def infinite(): Int = 1 + infinite()

  def printFunction(x : Int, y: => Int): Unit = println(x)

  //printFunction(infinite,34)
  printFunction(34, infinite)

  val aString : String= "Hello Scala"

  println(aString.tail.tail.tail.head)
  //String Operations
  println('a' +: "2" :+ 'z') //prepend and postpend operators

  def Sample(x: Int) = if(x>3) 5 else  x + "Hello"

}
