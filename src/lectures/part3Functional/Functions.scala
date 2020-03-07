package lectures.part3Functional

object Functions extends App {

  val stringConcatinator : (String, String) => String = new Function2[String, String, String] {
    override def apply(str1 : String, str2: String ) : String= str1+ " " +str2
  }

  println(stringConcatinator("vivek", "agrawal"))
}
