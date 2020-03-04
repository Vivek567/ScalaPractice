object CallByValueName extends App {

  def callByValue (x: Long): Unit = {
    println("By value: " + x)
    println("By value: " + x)
  }

  def callByNme (x: => Long): Unit = {
    println("By name: " + x)
    println("By name: " + x)
  }

  callByValue(System.nanoTime())
  callByNme(System.nanoTime())

}
