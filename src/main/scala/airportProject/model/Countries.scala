package fileReader.model

import scala.util.Try

//countries need:
//id,code,name,continent,wikipedia link
object Continent {
  sealed abstract class ContinentCode(val i: Int, val text: String) {
    def getContinentFromString(s: String): Option[ContinentCode] = s.match {
      case s"EU"  =>  Some(Continent.Europe)
      case s"AS"  =>  Some(Continent.Asia)
      case s"NA"  =>  Some(Continent.North_America)
      case s"AF"  =>  Some(Continent.Africa)
      case s"AN"  =>  Some(Continent.Antartica)
      case s"SA"  =>  Some(Continent.South_America)
      case s"OC"  =>  Some(Continent.Oceania)
    }
  }
  case object Europe extends ContinentCode(1, "Europe")
  case object Asia extends ContinentCode(2, "Asia")
  case object North_America extends ContinentCode(3, "North_America")
  case object Africa extends ContinentCode (4, "Africa")
  case object Antartica extends ContinentCode (5, "Antartica")
  case object South_America extends ContinentCode(6, "South_America")
  case object Oceania extends ContinentCode(7, "Oceania")

  import scala.Enumeration
  val continentCodes: Set[ContinentCode] = sealedInstancesOf[ContinentCode]
}
class Country(
  id: Long, 
  code: String, 
  name: String, 
  continent: Continent,
  wikipediaLink: String = "",
  keywords: String = ""
  )
object Country:
  def parseCountry(line: Array[String]): Option[Country] =
    (Try(line(0).toLong).toOption, line(1), line(2), line(3)).match {
      //check for how to implement toEither instead? for more error handling?
      case (Some(i), code, name, cnt) =>
        Some(Country(i, code, name, cnt))
      case (None, _, _, _) => None
    }
