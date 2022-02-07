package airportProject.model

import scala.util.Try

//countries need:
//id,code,name,continent,wikipedia link

class Country(
  val id: Long, 
  val code: String, 
  val name: String, 
  val continent: ContinentType,
  val wikipediaLink: String = "",
  val keywords: String = ""
  )
object Country:
  def parseCountry(line: Array[String]): Option[Country] =
    (Try(line(0).toLong).toOption, line(1), line(2), line(3)).match {
      //check for how to implement toEither instead? for more error handling?
      case (Some(i), code, name, cnt) =>
        Some(Country(i, code, name, cnt))
      case (None, _, _, _) => None
    }
