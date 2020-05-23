package lectures.part3Functional

import jdk.internal.util.xml.impl.Pair

import scala.util.Random

object CollectionSeq extends App {

  val aSeq = Seq(1, 2, 4, 3)
  println(aSeq)
  println(aSeq.reverse)
  println(aSeq ++ Seq(4, 5, 6))
  println(aSeq.diff(Seq(3, 4)))
  println(aSeq.sorted)

  val aList = List(1, 2, 3, 4)
  println(42 :: aList)
  println(42 +: aList)
  println(aList :+ 42)
  println(List.fill(5)("vivek "))
  println(aList.mkString("-!-"))

  val aArray = Array(1, 2, 3, 4)
  println(aArray)
  val aTuple = (5, "Scala")
  val bTuple = Tuple2(6, "Scala")
  val cTuple = 7 -> "Scala"

  println(aTuple)
  println(bTuple)
  println(cTuple)

  val aMap: Map[Int, String] = Map(aTuple, bTuple, cTuple)
  println(aMap)
  println(aMap.map(pair => pair._1 + 1 -> pair._2))

  val config: Map[String, String] = Map(
    "host" -> "172.54.43.1",
    "port" -> "80"
  )

  class Connection {
    def connected = "Connected"
  }

  object Connection {
    val random = new Random(System.nanoTime)

    def apply(host: String, port: String): Option[Connection] = {
      if (random.nextBoolean) Some(new Connection)
      else None
    }
  }

  val host = config.get("host")
  val port = config.get("port")
  println(host)
  println(port)
  val connection = host.flatMap(h => port.flatMap(p => Connection.apply(h, p)))
  val ConnectionStatus = connection.map(c => c.connected)
  println(ConnectionStatus)

val connectionStatus1 = for {
  h <- host
  p <- port
  c <- Connection(h,p)
} yield c.connected
  println(connectionStatus1)
}
