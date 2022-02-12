package airportProject.service

import java.nio.file.{Files, Path}
import scala.jdk.CollectionConverters.IteratorHasAsScala

final case class ReadResult[A](
    val validLines: List[A],
    val invalidLines: List[InvalidLine]
):
  def print = printf(
    validLines.length.toString + "\t" + invalidLines.length.toString + "\n"
  )

opaque type InvalidLine = (String, String)
object InvalidLine:
  def apply(error: String, s: String): InvalidLine = (error, s)
  def print(il: InvalidLine): String = il._1 + "\t incorrect element : " + il._2
  def getElement(il: InvalidLine): String = il._2
  def getError(il: InvalidLine): String = il._1

object CSV:

  def read[A](
      fileName: String,
      parseLine: Array[String] => Either[InvalidLine, A],
      regex: String = ","
  ) = {
    val (parsedLine, invalidLine) = Option(
      Files.lines(Path.of(s"src/main/data/$fileName"))
    )
      .map(_.iterator().asScala)
      .getOrElse(Iterator.empty) // if file can't be read option will be a none.
      .drop(1) // drop csv header
      .map(
        _.split(regex, -1)
      ) //you may want to use a regexp here. Also, -1 to keep trailing space (easier time later)
      .map(
        _.map(_.replace("\"", "").trim)
      )
      .map(parseLine)
      .partition(_.isRight)
    ReadResult(
      parsedLine.map(_.right.get).toList,
      invalidLine.map(_.left.get).toList
    )
  }
