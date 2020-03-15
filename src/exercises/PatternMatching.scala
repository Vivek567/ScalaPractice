package exercises

object PatternMatching extends App {
  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(x: Expr, y: Expr) extends Expr
  case class Prod(a: Expr, b: Expr) extends Expr

  def show(e1: Expr) : String = {
    val expression = e1 match {
      case Number(n) => n.toString
      case Sum(x, y) => show(x) + " + " + show(y)
      case Prod(a, b) => {
        def mayBeShowParenthesis(exp: Expr) : String = exp match {
          case Number(_) => show(exp)
          case Prod(_, _) => show(exp)
          case _=> "(" + show(exp) +") "
        }
        mayBeShowParenthesis(a) + " * " + mayBeShowParenthesis(b)
      }
      case _ => "wrong expression"
    }

    expression
  }

  println(show(Sum(Number(5), Number(6))))
  println(show(Prod(Number(5), Number(6))))
  println(show(Prod(Sum(Number(5), Number(6)), Number(6))))
}
