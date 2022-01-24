package fileReader.model

import scala.util.Try

//airports needs :
//id,ident,type,name,continent,iso_country,iso_region,municipality,gps_code
object AirportType {
  sealed abstract class Type(val i: Int, val text: String) {
    def getAirportTypeFromString(s: String): Option[Type] = s.match {
      case s"heliport"       => Some(AirportType.heliport)
      case s"small_airport"  => Some(AirportType.small_airport)
      case s"medium_airport" => Some(AirportType.medium_airport)
      case s"large_airport"  => Some(AirportType.large_airport)
      case s"balloonport"    => Some(AirportType.balloonport)
      case s"seaplane_base"  => Some(AirportType.seaplane_base)
      case s"closed"         => Some(AirportType.closed)
    }
  }
  case object heliport extends Type(1, "heliport")
  case object small_airport extends Type(2, "small_airport")
  case object medium_airport extends Type(3, "medium_airport")
  case object large_airport extends Type(4, "large_airport")
  case object balloonport extends Type(5, "balloonport")
  case object seaplane_base extends Type(6, "seaplane_base")
  case object closed extends Type(7, "closed")

  import scala.Enumeration
  val airportTypes: Set[Type] = sealedInstancesOf[Type]
}

class Airport(
    id: Long,
    ident: String,
    airporttype: AirportType,
    name: String,
    continent: String
)
object Airport:
  def parseAirport(line: Array[String]): Option[Airport] =
    (Try(line(0).toLong).toOption, line(1), line(2), line(3)).match {
      //check for how to implement toEither instead? for more error handling?
      case (Some(i), code, name, cnt) =>
        Some(Airport(i, code, name, cnt))
      case (None, _, _, _) => None
    }
