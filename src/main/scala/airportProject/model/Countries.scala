package airportProject.model

import scala.util.Try
import airportProject.service._
class Country(
    id: Long,
    code: NonEmptyString,
    name: NonEmptyString,
    continent: ContinentType,
    wikipediaLink: Option[NonEmptyString],
    keywords: Option[NonEmptyString]
)
object Country:
  def parseCountry(line: Array[String]): Either[InvalidLine, Country] =
    (
      Try(line(0).toLong).toOption,
      NonEmptyString.orNone(line(1)),
      NonEmptyString.orNone(line(2)),
      Try(ContinentType.valueOf(line(3))).toOption
    ).match {
      //check for how to implement toEither instead? for more error handling?
      case (Some(i), Some(code), Some(name), Some(cnt)) =>
        Right(
          Country(
            i,
            code,
            name,
            cnt,
            NonEmptyString.orNone(line(4)),
            NonEmptyString.orNone(line(5))
          )
        )
      case (None, _, _, _) => Left(InvalidLine("", line(0)))
      case (_, None, _, _) => Left(InvalidLine("", line(0)))
      case (_, _, None, _) => Left(InvalidLine("", line(0)))
      case (_, _, _, None) => Left(InvalidLine("", line(0)))
    }
