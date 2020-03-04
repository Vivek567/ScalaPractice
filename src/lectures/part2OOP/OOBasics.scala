package lectures.part2OOP

object OOBasics extends App {

  class Writer(firstName: String, surName: String, val year: Int)
  {
    def fullName = firstName +" " + surName
  }

  class Novel(name: String, yearOfRelease: Int, author: Writer)
  {
    def authorAge = author.year
    def isWrittenBy(auth: Writer) : Boolean = auth.fullName.equals(author.fullName)
    def copy(yearOfRel: Int) : Novel =new Novel(name, yearOfRel,author)
  }
}
