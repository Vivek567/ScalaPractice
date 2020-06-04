package Advanced

object DiamondProblem extends App {

  trait Animal {
    def name: String
  }

  trait Lion extends Animal {
    override def name: String = "lion"
  }

  trait Tiger extends Animal {
    override def name: String = "tiger"
  }

  class Mutant extends Animal with Tiger with Lion // Mutant extends Animal extends Animal { override def name: String = "tiger"} with Animal {  override def name: String = "lion"}

  val mutant = new Mutant
  println(mutant.name) // last override gets picked


}
