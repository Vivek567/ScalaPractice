package exercises

object MapFlatMap extends App {

  val number = List(1, 2, 3, 4)
  val chars = List('a', 'b', 'c', 'd')

  val generteCombiniations: (List[Char], List[Int]) => List[String] = {
    //def mapper (x: Char, Y: List[Int]) : List[String] = Y.map(y=> x+""+y)

    (A, B) => A.flatMap(a => B.map(b => a + "" + b))
  }

  println(generteCombiniations(chars, number))
  println(number.map(n=> n+1).toString)
  println(number.flatMap(n=>List(n+1)).toString)
  println(number.flatMap(n=>List(n, n+1)).toString)
}