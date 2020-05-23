package lectures.part4PatternMatching

//19-May-2020
object pm extends App {

  trait Expr

  case class Number(n: Int) extends Expr

  case class Sum(a: Expr, b: Expr) extends Expr

  case class Prod(a: Expr, b: Expr) extends Expr

  val expr: Expr = Prod(Number(3), Sum(Number(4), Prod(Number(5), Sum(Number(6), Number(7)))))
  val expr1 =  Prod(Sum(Number(3), Number(4)),Sum(Number(3), Number(4)))
  def description(expr: Expr): String = expr match {
    case Number(n) => n.toString
    case Sum(a, b) => s"${description(a)} + ${description(b)}"
    case Prod(a, b) => {
      def showSum(e:Expr) = e match {
        case Sum(a, b) => s"(${description(a)} + ${description(b)})"
        case _ => description(e)

      }
      s"${showSum(a)} * ${showSum(b)}"
    }
    case _ => "Invalid Expression"
  }

  println(description(expr1))
}
