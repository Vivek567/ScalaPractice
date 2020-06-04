package Advanced

object Variance extends App {

  class Animal

  class Cat extends Animal

  //1. Covariant

  class CCage[+T]

  val CCage: CCage[Animal] = new CCage[Cat]

  //2. Invariant

  class ICage[T]

  val ICage: ICage[Animal] = new ICage[Animal]

  //3. ContraVariant
  class Cage[-T]

  val cage: Cage[Cat] = new Cage[Animal]

  var greeting: Option[String] = Some("hello")
  greeting = Some("7")
  println(greeting.get)

  val cool = Map("a" -> "aaa", "b" -> "bbb", "a" -> "ccc")
  println(cool("a"))

  println(40.getClass)


  def sad = "meow"

  val catCry = sad
  println(catCry)

  var x, y, z = (1, 2, 3)
  println(x)
  println(y)
  println(z)


  def f(arr: List[Int]): List[Int] = {
    /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution
*/
    (0 to arr.length).filter(x=> x%2==0).toList.flatMap(i=> List(arr(i)))
//   val o=  for {
//      x <- (0 to arr.length-1) if x % 2 == 0
//    } yield List(arr(x))
//
//    o.flatten.toList
  }

  println(f(List(1, 5, 3, 4, 5, 6, 7)))
  println(List(1, 5, 3, 4, 5, 6, 7).find(p=>p%2==0))
  println(List(1, 5, 3, 4, 5, 6, 7).find(p=>p%2!=0))
  def printSeriesSum(n:Double): Double = {





    def sumOfSeries(x: Double): Double = {
      (1 to 9).map(x=>x*1.0).fold(1.0)((i, j)  => {

        val exp =Math.pow(x, j)
        val fact= factorial(j, 1.0)
        i +  exp/fact

    })
    }
    def factorial(z: Double, acc: Double): Double = {
      if (z <= 1) acc * 1.0
      else factorial(z - 1, z * acc)
    }

    sumOfSeries(n)
  }

  println(printSeriesSum(20.000))
}
