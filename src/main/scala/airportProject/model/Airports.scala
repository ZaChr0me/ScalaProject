package airportProject.model

import scala.util.Try
import scala.util.Failure
import airportProject.service._
//airports needs :
//id,ident,type,name,continent,iso_country,iso_region,municipality,gps_code

class Airport(
    val id: Int, //0
    val ident: NonEmptyString, //1
    val airportType: AirportType, //2
    val name: NonEmptyString, //3
    val continent: ContinentType, //7
    val isoCountry: IsoCountryType, //8
    val isoRegion: IsoRegionType, //9
    val municipality: NonEmptyString, //10
    val scheduledService: Option[Boolean], //11
    val gpsCode: Option[NonEmptyString], //12
    val iataCode: Option[NonEmptyString], //13
    val localCode: Option[NonEmptyString], //14
    val homeLink: Option[NonEmptyString], //15
    val wikipediaLink: Option[NonEmptyString], //16
    val keywords: Option[NonEmptyString] //17
)
//TODO recheck later what is optional and what is absolutely needed
object Airport:
  def parseAirport(line: Array[String]): Either[InvalidLine, Airport] =
    (
      Try(line(0).toInt).toOption,
      NonEmptyString.orNone(line(1)),
      Try(AirportType.valueOf(line(2).replace("_", ""))).toOption,
      NonEmptyString.orNone(line(3)),
      Try(ContinentType.valueOf(line(7))).toOption,
      IsoCountryType.orNone(line(8)),
      IsoRegionType.orNone(line(9)),
      NonEmptyString.orNone(line(10))
    ).match {
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
        Right(
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
      case (None, _, _, _, _, _, _, _) => Left(InvalidLine("", line(0)))
      case (_, None, _, _, _, _, _, _) => Left(InvalidLine("", line(1)))
      case (_, _, None, _, _, _, _, _) => Left(InvalidLine("", line(2)))
      case (_, _, _, None, _, _, _, _) => Left(InvalidLine("", line(3)))
      case (_, _, _, _, None, _, _, _) => Left(InvalidLine("", line(7)))
      case (_, _, _, _, _, None, _, _) => Left(InvalidLine("", line(8)))
      case (_, _, _, _, _, _, None, _) => Left(InvalidLine("", line(9)))
      case (_, _, _, _, _, _, _, None) => Left(InvalidLine("", line(10)))
    }
