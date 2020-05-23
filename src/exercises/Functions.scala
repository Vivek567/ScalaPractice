package exercises

object Functions extends App {

  def greetings(name: String, age: Int): String = s"Hi, my name is $name and i am $age years old"

  println(greetings("Vivek", 35))

  def factorial(n: Int): Int = {
    if (n <= 0) 1
    else n * factorial(n - 1)
  }

  println(factorial(5))

  // 1, 1, 2, 3, 5, 8, 13
  def fibonacci(f: Int): Int = {
    if (f < 3) 1
    else  fibonacci(f - 1) +  fibonacci(f- 2)
  }

  //6=> fib(5)+ fib(4)=> fib(4)+fib(3) +  fib(3)+fib(2)=> fib(3)+fib(2) + fib(2)+ fib(1)  + fib(2)+ fib(1) + fib(2) => fib(2)+ fib(1) +fib(2) + fib(2)+ fib(1)  + fib(2)+ fib(1) + fib(2) => 1+1+1+1+1+1+1+1
  println(fibonacci(6))

  println(fibonacci(7))
}
