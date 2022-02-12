package airportProject.model
import airportProject.service._
import scala.util.Try
//Runways need :
//id,airport ref,airport ident,surface,

class Runway(
    id: Long,
    airportRef: Long,
    airportIdent: AirportIdentType,
    surface: NonEmptyString,
    leIdent: AirportLeIndentType,
    length: Option[Int], //3
    width: Option[Int], //4
    lighted: Option[Boolean], //6
    closed: Option[Boolean], //7
    leLatitude: Option[String], //9
    leLongitude: Option[String], //10
    leElevation: Option[Int], //11
    leHeadingDegT: Option[String], //12
    leDisplacedThreshold: Option[Int], //13
    heIdent: Option[String], //14
    heLatitude: Option[String], //15
    heLongitude: Option[String], //16
    heElevation: Option[Int], //17
    heHeadingDegT: Option[String], //18
    heDisplacedThreshold: Option[Int] //19
)

object Runway:
  def parseRunway(line: Array[String]): Option[Runway] =
    (
      Try(line(0).toLong).toOption,
      Try(line(1).toLong).toOption,
      AirportIdentType.orNone(line(5)),
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
        Some(
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
      case (None, _, _, _, _) => None
      case (_, None, _, _, _) => None
      case (_, _, None, _, _) => None
      case (_, _, _, None, _) => None
      case (_, _, _, _, None) => None
    }
