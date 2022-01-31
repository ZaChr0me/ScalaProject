package airportProject.model

import scala.util.Try
import scala.util.Failure

//airports needs :
//id,ident,type,name,continent,iso_country,iso_region,municipality,gps_code


class Airport(
    id: Int,
    ident: String,
    airportType: AirportType,
    name: String,
    continent: ContinentType,
    isoCountry:String,
    isoRegion:String,
    municipality:String,
    gpsCode:String="",
    localCode:String="",
    latitudeDeg:Double=0.0,
    longitudeDeg:Double=0.0,
    elevationFt:Double=0.0,
    scheduledService:Boolean=false,
    iataCode:String="",
    homeLink:String="",
    wikipediaLink:String="",
    keywords:String=""


)
//TODO recheck later what is optional and what is absolutely needed
object Airport:
  def parseAirport(line: Array[String]): Option[Airport] =
    (Try(line(0).toInt).toOption,
     line(1),
      Try(AirportType.valueOf(line(2))).toOption,
       line(3),
        Try(AirportType.valueOf(line(7))).toOption,
        line(8),
        line(9),
        line(10)).match {
      //check for how to implement toEither instead? for more error handling?
      case (Some(i), ident, Some(ct), name,  Some(ctn),isoC,isoR,municip) =>
        Some(Airport(i,ident,at,name,ct,isoC,isoR,municip))
      case (None, _, _, _, _) => None//line 0 alias ID is incorrect
      case (_,_,Some(at),_,_)=>None//line 2 alias the airport type is incorrect
    }
    def temp()={
      val aTest:AirportType=AirportType.Heliport
      aTest.toString
    }
