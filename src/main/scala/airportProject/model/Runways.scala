package airportProject.model

//Runways need :
//id,airport ref,airport ident,surface,

class Runway(
    val id: Long,
    val airportRef: Long,
    val airportIdent: String,
    val surface: String,
    val length: Int = 0,
    val width: Int = 0,
    val lighted: Int = 0,
    val closed: Int = 0,
    val leIdent: String = "",
    val leLatitude: Float = 0,
    val leLongitude: Float = 0,
    val leElevation: Int = 0,
    val leHeadingDegT: Float = 0,
    val leDisplacedThreshold: Int = 0,
    val heIdent: String = "",
    val heLatitude: Float = 0,
    val heLongitude: Float = 0,
    val heElevation: Int = 0,
    val heHeadingDegT: Float = 0,
    val heDisplacedThreshold: Int = 0
)

object Runway:
    def parseRunway(line: Array[String]): Option[Runway] =
        (Try(line(0).toLong).toOption, Try(line(1).toLong).toOption, line(2), line(5)).match {
            case (Some(id), Some(airportId), airportIdent, surface) =>
                Some(Runway(id, airportId, airportIdent, surface))
            case (None, _, _, _) => None
        }