package exerciseadv

object PartialFunc extends App{

  val myFirstPF : PartialFunction[Int, String] = {
    case 1 => "One"
    case 2 => "Two"
    case 3 => "Three"
  }
  println(myFirstPF(3))
  println(myFirstPF.isDefinedAt(4))
  val anotherFunc = myFirstPF.orElse[Int, String] {
    case 4 => "Four"
  }
  println(anotherFunc(4))

  val list = List(1,2,3).map {
    case 1 => "One"
    case 2 => "Two"
    case 3 => "Three"
  }

  println(list)

  val chatBot : PartialFunction[String, String] = {
    case "Hi" =>  "Hello !!! How are you?"
    case "Name" => "My name is scala"
    case "age" => "I am 32 years old"
    case _ => "I don't understand"
  }

  scala.io.Source.stdin.getLines().foreach(line=> println(chatBot(line)))
}
