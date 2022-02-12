package airportProject.model
import airportProject.service._
import scala.util.Try
//Runways need :
//id,airport ref,airport ident,surface,

class Runway(
    val id: Long,
    val airportRef: Long,
    val airportIdent: NonEmptyString,
    val surface: NonEmptyString,
    val leIdent: AirportLeIndentType,
    val length: Option[Int], //3
    val width: Option[Int], //4
    val lighted: Option[Boolean], //6
    val closed: Option[Boolean], //7
    val leLatitude: Option[String], //9
    val leLongitude: Option[String], //10
    val leElevation: Option[Int], //11
    val leHeadingDegT: Option[String], //12
    val leDisplacedThreshold: Option[Int], //13
    val heIdent: Option[String], //14
    val heLatitude: Option[String], //15
    val heLongitude: Option[String], //16
    val heElevation: Option[Int], //17
    val heHeadingDegT: Option[String], //18
    val heDisplacedThreshold: Option[Int] //19
)

object Runway:
  def parseRunway(line: Array[String]): Either[InvalidLine, Runway] =
    (
      Try(line(0).toLong).toOption,
      Try(line(1).toLong).toOption,
      NonEmptyString.orNone(line(5)),
      NonEmptyString.orNone(line(2)),
      AirportLeIndentType.orNone(line(8))
    ).match {
      case (
            Some(id),
            Some(airportId),
            Some(airportIdent),
            Some(surface),
            Some(leIndent)
          ) =>
        Right(
          Runway(
            id,
            airportId,
            airportIdent,
            surface,
            leIndent,
            line(3).toIntOption,
            line(4).toIntOption,
            StringToBoolean(line(6)),
            StringToBoolean(line(7)),
            Option(line(9)),
            Option(line(10)),
            line(11).toIntOption,
            Option(line(12)),
            line(13).toIntOption,
            Option(line(14)),
            Option(line(15)),
            Option(line(16)),
            line(17).toIntOption,
            Option(line(18)),
            line(19).toIntOption
          )
        )
      case (None, _, _, _, _) => Left(InvalidLine("", line(0)))
      case (_, None, _, _, _) => Left(InvalidLine("", line(1)))
      case (_, _, None, _, _) => Left(InvalidLine("", line(5)))
      case (_, _, _, None, _) => Left(InvalidLine("", line(2)))
      case (_, _, _, _, None) => Left(InvalidLine("", line(8)))
    }
