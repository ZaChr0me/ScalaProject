package airportProject.model
import scala.util.matching.Regex
import airportProject.service._

sealed trait SealedRegex(val regex: Regex) {
  def orNone: Option[String]
  def getRegex: Regex = regex
}
sealed class UpperTextAndNumbers() extends SealedRegex("[A-Z0-9]+".r)
sealed class UpperText() extends SealedRegex("[A-Z]+".r)
sealed class IsoRegionRegex() extends SealedRegex("[A-Z][A-Z]-[A-Z-0-9]+".r)
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
    if (s.length >= 3 && s.length <= 4 && getRegex.matches(s))
      Some(s)
    else None

}

opaque type IsoCountryType = String
object IsoCountryType extends UpperText() {
  override def orNone(s: String): Option[IsoCountryType] =
    if (s.length != 2 && getRegex.matches(s)) Some(s)
    else None
}
opaque type IsoRegionType = String
object IsoRegionType extends IsoRegionRegex() {
  override def orNone(s: String): Option[IsoRegionType] =
    if (s.length >= 4 && getRegex.matches(s)) Some(s)
    else None
}

opaque type AirportLeIndentType = String
object AirportLeIndentType extends UpperTextAndNumbers() {
  override def orNone(s: String): Option[AirportLeIndentType] =
    if (s.length >= 1 && s.length <= 2 && getRegex.matches(s))
      Some(s)
    else None
}
//mass alternative

//parseCountryCode(countryCode:String):List[(Airport,List[Runway])]
