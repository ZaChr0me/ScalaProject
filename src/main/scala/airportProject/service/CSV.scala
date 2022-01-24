package airportProject.service

import java.nio.file.{Files, Path}
import scala.jdk.CollectionConverters.IteratorHasAsScala


final case class ReadResult[A](lines: Iterator[A], nbInvalidLine: Int)

object CSV:

  def read[A](fileName: String, parseLine: Array[String] => Either[A], regex: String = ",") =
    val (parsedLine, invalidLine) = Option(Files.lines(Path.of(s"src/main/data/$fileName")))
      .map(_.iterator().asScala)
      .getOrElse(Iterator.empty) // if file can't be read option will be a none.
      .drop(1)  // drop csv header
      .map(_.split(regex)) //you may want to use a regexp here
      .map(_.map(_.trim))
      .map(parseLine)
      .partition(_.isRight)

    ReadResult(parsedLine.collect((Right(r)->r)), invalidLine.collect(Left(l)->l))


