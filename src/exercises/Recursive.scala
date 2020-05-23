package exercises

import scala.annotation.tailrec

object Recursive extends App {


  /*
    1.  Concatenate a string n times
    2.  IsPrime function tail recursive
    3.  Fibonacci function, tail recursive.
   */

  def stringConcate(aString: String, n: Int): String = {
    def stringHelper(param: Int, acc: String): String = {
      if (param == 1) acc
      else stringHelper(param - 1, aString + acc)
    }

    stringHelper(n, aString)
  }

  println(stringConcate("Hi ", 5))


  def isPrime(n: Int): Boolean = {
    @tailrec
    def isPrimeUntil(t: Int, acc: Boolean): Boolean = {
      if (!acc) false
      if (t == 1) true
      else isPrimeUntil(t - 1, n % t != 0 && acc)
    }

    isPrimeUntil(n / 2, true)
  }

  println(isPrime(2003))
  println(isPrime(37))
  println(isPrime(17 * 37))
  //println(17 / 2)


  def fib(n: Int): Int = {
    def fibTailRec(i: Int,  last: Int, nextLast: Int): Int = {
      if (i >= n) last
      else fibTailRec(i + 1, last + nextLast, last)
    }

    if (n <= 2) 1
    else fibTailRec(3, 2, 1)
  }

  println(fib(8))
}
