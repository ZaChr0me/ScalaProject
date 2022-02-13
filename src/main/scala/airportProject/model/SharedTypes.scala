package airportProject.model
import scala.util.matching.Regex
import airportProject.service._

sealed trait SealedRegex(val regex: Regex) {
  def getRegex: Regex = regex
}
sealed abstract class UpperTextAndNumbers() extends SealedRegex("[A-Z0-9]+".r)
sealed abstract class UpperText() extends SealedRegex("[A-Z]+".r)
sealed abstract class IsoRegionRegex()
    extends SealedRegex("[A-Z][A-Z]-[A-Z-0-9]+".r)
//lowercase are needed to convert from strings
enum AirportType {
  case heliport, smallairport, mediumairport, largeairport, balloonport,
  seaplanebase, closed
}

enum ContinentType {
  case AF, AN, AS, EU, NA, OC, SA
}

opaque type IsoCountryType = String
object IsoCountryType extends UpperText() {
  def orNone(s: String): Option[IsoCountryType] =
    if (s.length == 2 && getRegex.matches(s))
      Some(s)
    else None
}
opaque type IsoRegionType = String
object IsoRegionType extends IsoRegionRegex() {
  def orNone(s: String): Option[IsoRegionType] =
    if (s.length >= 4 && getRegex.matches(s))
      Some(s)
    else None
}

opaque type AirportLeIndentType = String
object AirportLeIndentType extends UpperTextAndNumbers() {
  def orNone(
      s: String
  ): Option[AirportLeIndentType] =
    if (s.length >= 1 && s.length <= 2 && getRegex.matches(s))
      Some(s)
    else None
}
