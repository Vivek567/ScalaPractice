import scala.annotation.tailrec

object Recursion extends App {

  def factorial(n: Int): Int = {
    if (n <= 1) 1
    else {
      println("I need to compute factorial of " + n + " .To compute it i need to know factorial of " + (n - 1))
      val result = n * factorial(n - 1)
      println("computed factorial of " + n)
      result
    }

  }

  //println(factorial(6000))

  def anotherFactorial(n: Int): BigInt = {
    def factorialAux(x: Int, accumulator: BigInt): BigInt = {
      if (x <= 1) accumulator
      else factorialAux(x - 1, x * accumulator)
    }

    factorialAux(n, 1)
  }

  //println(anotherFactorial(5000))


  def stringConcat(s: String, n: Int): String = {
    @tailrec
    def stringConcatAux(x: Int, accum: String): String = {
      if (x <= 1) accum
      else stringConcatAux(x - 1, s + accum)
    }

    stringConcatAux(n, s)
  }

  println(stringConcat("Hello ", 15))

  def isPrime(n: Int): Boolean = {
    @tailrec
    def isPrimeAux(x: Int, isStillPrime: Boolean): Boolean = {
      if (!isStillPrime) false
      else if (x <= 1) true
      else isPrimeAux(x - 1, n % x != 0 && isStillPrime)
    }

    isPrimeAux(n / 2, true)
  }

  println(isPrime(7))

  def fibonacci(n: Int): Int = {
    @tailrec
    def fibAux(x: Int, acc: Int, acc1: Int): Int = {
      if (x <= 2) acc
      else fibAux(x - 1, acc + acc1, acc)
    }

    fibAux(n, 1, 1)
  }

  println(fibonacci(7))
  // 1, 1, 2, 3, 5, 8 , 13
}
