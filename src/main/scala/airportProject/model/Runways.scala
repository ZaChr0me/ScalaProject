package fileReader.model

//Runways need :
//id,airport ref,airport ident,surface,

class Runway(
    id: Long,
    airportRef: Long,
    airportIdent: String,
    surface: String,
    length: Int = 0,
    width: Int = 0,
    lighted: Int = 0,
    closed: Int = 0,
    leIdent: String = "",
    leLatitude: Float = 0,
    leLongitude: Float = 0,
    leElevation: Int = 0,
    leHeadingDegT: Float = 0,
    leDisplacedThreshold: Int = 0,
    heIdent: String = "",
    heLatitude: Float = 0,
    heLongitude: Float = 0,
    heElevation: Int = 0,
    heHeadingDegT: Float = 0,
    heDisplacedThreshold: Int = 0
)

object Runway:
    def parseRunway(line: Array[String]): Option[Runway] =
        (Try(line(0).toLong).toOption, Try(line(1).toLong).toOption, line(2), line(5)).match {
            case (Some(id), Some(airportId), airportIdent, surface) =>
                Some(Runway(id, airportId, airportIdent, surface))
            case (None, _, _, _) => None
        }