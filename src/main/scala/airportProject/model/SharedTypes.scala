package airportProject.model
import scala.util.matching.Regex
import airportProject.service._

sealed trait UpperTextAndNumbers(val regex: Regex = "[A-Z0-9]+".r) {
  def orNone: Option[String]
}

enum AirportType {
  case Heliport, SmallAirport, MediumAirport, LargeAirport, Balloonport,
  SeaplaneBase, Closed
}

enum ContinentType {
  case AF, AN, AS, EU, NA, OC, SA
}
opaque type AirportIdentType = String
object AirportIdentType extends UpperTextAndNumbers() {
  override def orNone(s: String): Option[AirportIdentType] =
    if (s.length >= 3 && s.length <= 4 && regex.matches(s))
      Some(s)
    else None

}
opaque type AirportLeIndentType = String

object AirportLeIndentType extends UpperTextAndNumbers() {
  override def orNone(s: String): Option[AirportLeIndentType] =
    if (s.length >= 1 && s.length <= 2 && regex.matches(s))
      Some(s)
    else None
}
//mass alternative

//parseCountryCode(countryCode:String):List[(Airport,List[Runway])]
