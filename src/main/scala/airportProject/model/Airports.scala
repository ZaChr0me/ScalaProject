package airportProject.model

import scala.util.Try
import scala.util.Failure
import airportProject.service._
//airports needs :
//id,ident,type,name,continent,iso_country,iso_region,municipality,gps_code

class Airport(
    id: Int, //0
    ident: AirportIdentType, //1
    airportType: AirportType, //2
    name: NonEmptyString, //3
    continent: ContinentType, //7
    isoCountry: IsoCountryType, //8
    isoRegion: IsoRegionType, //9
    municipality: NonEmptyString, //10
    scheduledService: Option[Boolean], //11
    gpsCode: Option[NonEmptyString], //12
    iataCode: Option[NonEmptyString], //13
    localCode: Option[NonEmptyString], //14
    homeLink: Option[NonEmptyString], //15
    wikipediaLink: Option[NonEmptyString], //16
    keywords: Option[NonEmptyString] //17
)
//TODO recheck later what is optional and what is absolutely needed
object Airport:
  def parseAirport(line: Array[String]): Option[Airport] =
    (
      Try(line(0).toInt).toOption,
      AirportIdentType.orNone(line(1)),
      Try(AirportType.valueOf(line(2))).toOption,
      NonEmptyString.orNone(line(3)),
      Try(ContinentType.valueOf(line(7))).toOption,
      IsoCountryType.orNone(line(8)),
      IsoRegionType.orNone(line(9)),
      NonEmptyString.orNone(line(10))
    ).match {
      //check for how to implement toEither instead? for more error handling?
      case (
            Some(i),
            Some(ident),
            Some(at),
            Some(name),
            Some(ctn),
            Some(isoC),
            Some(isoR),
            Some(municip)
          ) =>
        Some(
          Airport(
            i,
            ident,
            at,
            name,
            ctn,
            isoC,
            isoR,
            municip,
            StringToBoolean(line(11)),
            NonEmptyString.orNone(line(12)),
            NonEmptyString.orNone(line(13)),
            NonEmptyString.orNone(line(14)),
            NonEmptyString.orNone(line(15)),
            NonEmptyString.orNone(line(16)),
            NonEmptyString.orNone(line(17))
          )
        )

      case (None, _, _, _, _) => None //line 0 alias ID is incorrect
      case (_, _, Some(at), _, _) =>
        None //line 2 alias the airport type is incorrect
    }
