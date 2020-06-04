package Advanced

import java.util.concurrent.Executors

object Concurrency extends App {
  //
  //  val aThread = new Thread(
  //    new Runnable {
  //      override def run(): Unit = println("Hi I am running in a thread")
  //    }
  //  )
  //
  //  aThread.start()
  //
  //  val helloThread = new Thread(
  //    () => (1 to 5).foreach(x => println("Hello"))
  //  )
  //
  //
  //  val byeThread = new Thread(
  //    () => (1 to 5).foreach(x => println("Goodbye"))
  //  )
  //
  //  helloThread.start
  //  byeThread.start
  //
  //  val pool = Executors.newFixedThreadPool(10)
  //
  //  pool.execute(() => {
  //    Thread.sleep(1000)
  //    println("sleeps for 1 sec")
  //  })
  //
  //  pool.execute(() => {
  //    Thread.sleep(1000)
  //    println("done for 1 sec")
  //    Thread.sleep(1000)
  //    println("sleeps for 2 sec")
  //  })

  //pool.shutdown()
  //pool.shutdownNow()

  def inceptionThreads(n: Int): Unit = {
    if (n > 0) {
      val thread = new Thread(
        () => println(s"hello from thread # $n")
      )
      thread.start()
      thread.join()
      inceptionThreads(n - 1)
    }
  }

  inceptionThreads(50)
}
