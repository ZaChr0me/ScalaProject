package airportProject.service

import java.nio.file.{Files, Path}
import scala.jdk.CollectionConverters.IteratorHasAsScala

final case class ReadResult[A](
    validLines: Iterator[A],
    invalidLines: Iterator[InvalidLine]
)

opaque type InvalidLine = (String, String)
object InvalidLine:
  def apply(error: String, s: String): InvalidLine = (error, s)

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
      .map(_.split(regex)) //you may want to use a regexp here
      .map(_.map(_.replace("\"", "").trim))
      .map(parseLine)
      .partition(_.isRight)

    ReadResult(
      parsedLine.map(_.right.get),
      invalidLine.map(_.left.get)
    )
  }
