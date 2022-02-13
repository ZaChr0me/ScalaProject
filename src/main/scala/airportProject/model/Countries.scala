package airportProject.model

import scala.util.Try
import airportProject.service._
class Country(
    val id: Long,
    val code: IsoCountryType,
    val name: NonEmptyString,
    val continent: ContinentType,
    val wikipediaLink: Option[NonEmptyString],
    val keywords: Option[NonEmptyString]
)
object Country:
  def parseCountry(line: Array[String]): Either[InvalidLine, Country] =
    (
      Try(line(0).toLong).toOption,
      IsoCountryType.orNone(line(1)),
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
      case (None, _, _, _) =>
        Left(InvalidLine("Invalid id on line " + line.mkString(","), line(0)))
      case (_, None, _, _) => 
        Left(InvalidLine("Invalide Country code on line " + line.mkString(","), line(1)))
      case (_, _, None, _) => 
        Left(InvalidLine("Invalid name on line" + line.mkString(","), line(2)))
      case (_, _, _, None) => 
        Left(InvalidLine("Invalid Continent on line " + line.mkString(","), line(3)))
    }
