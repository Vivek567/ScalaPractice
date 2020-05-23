package lectures.part2OOP

object OOBasics extends App {

  class Writer(firstName: String, surName: String, val year: Int) {
    def fullName = firstName + " " + surName
  }

  class Novel(name: String, yearOfRelease: Int, author: Writer) {
    def authorAge = yearOfRelease - author.year

    def isWrittenBy(auth: Writer): Boolean = auth.fullName.equals(author.fullName)

    def copy(yearOfRel: Int): Novel = new Novel(name, yearOfRel, author)
  }

  //*
  class Counter(val num: Int) {
    def currentCount: Int = this.num

    def inc(x: Int = 1): Counter = new Counter(num + x)

    def dec(x: Int = 1): Counter = new Counter(num - x)

  }

  val counter = new Counter(5)
  println(counter.currentCount)
  println(counter.inc().currentCount)
  println(counter.dec().currentCount)
  println(counter.dec().currentCount)
  println(counter.inc(2).currentCount)

}
