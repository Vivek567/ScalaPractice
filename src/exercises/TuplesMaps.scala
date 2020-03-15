package exercises

object TuplesMaps extends App {

  val phoneBook = Map(("Jim"-> 555), ("JIM"->909))
  println(phoneBook)
  //println(phoneBook.map(pair => pair._1.toLowerCase -> pair._2))

  def add(network: Map[String, Set[String]], person: String) : Map[String , Set[String]] = {
    network + (person -> Set())
  }

  def friend(network: Map[String, Set[String]], a: String, b: String) : Map[String, Set[String]]= {
    network + (a -> (network(a) + b)) + (b -> (network(b) + a))
  }
  val jim : String = "Jim"
  val mary : String = "Mary"
  val john : String = "John"
  val empty :Map[String, Set[String]] = Map()
  val network = add(add(empty,jim),mary)
  println(friend(network,jim,mary))
  network.foreach(x=> println(x.toString))

}
