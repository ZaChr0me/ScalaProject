package airportProject.model

//Runways need :
//id,airport ref,airport ident,surface,

class Runway(
    id: Long,
    airportRef: Long,
    airportIdent: String,
    surface: String
)

object Runway:
    def parseRunway(line: Array[String]): Option[Runway] =
        (Try(line(0).toLong).toOption, Try(line(1).toLong).toOption, line(2), line(5)).match {
            case (Some(id), Some(airportId), airportIdent, surface) =>
                Some(Runway(id, airportId, airportIdent, surface))
            case _ => None
        }