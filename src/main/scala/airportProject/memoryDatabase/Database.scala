package airportProject.memoryDatabase

import airportProject.model._
import airportProject.service._
import scala.io.Source
import java.util.Locale._

class Database(
    val airports: List[Airport],
    val countries: List[Country],
    val runways: List[Runway]
) {

  def parseCountryCode(
      countryCode: IsoCountryType
  ): List[(Airport, List[Runway])] =
    airports
      .filter(airport => {
        airport.isoCountry == countryCode
      })
      .map(airport =>
        (
          airport,
          runways.filter(runway => {
            (runway.airportRef == airport.id) //problem with NonEmptyString comparison
          })
        )
      )

  def parseCountryName(
      countryName: NonEmptyString
  ): List[(Airport, List[Runway])] = {
    val country: Option[Country] =
      countries.find(country => country.name == countryName)
    if (country.isDefined)
      airports
        .filter(airport => { airport.isoCountry == country.get.code })
        .map(airport =>
          (
            airport,
            runways.filter { runway =>
              (runway.airportRef == airport.id)
            }
          )
        )
    else null //TODO
  }
  def parseReportCountries(): List[(Country, Int)] =
    countries
      .map(country =>
        (
          country,
          airports
            .filter(airport => (airport.isoCountry == country.code))
            .size
        )
      )
      .sortWith(_._2 > _._2)
      .filter((x, y) => y < 10 && y > (countries.size - 10))

  def parseReportSurface(): List[(Country, List[String])] =
    countries
      .map(country =>
        (
          country,
          runways
            .filter(runway =>
              airports
                .filter(airport => (airport.isoCountry == country.code))
                .contains(runway.airportIdent)
            )
            .map(_.surface)
            .map(_.toString.toUpperCase)
            .distinct
        )
      )

  def parseReportLatitude(): List[(String, Int)] =
    runways
      .map(runway =>
        (
          runway.leIdent.toString,
          runways.filter(x => x.leIdent == runway.leIdent).size
        )
      )
      .distinct
}

object Database {
  private def getValidPercent[A](rr: ReadResult[A]): Int =
    rr.validLines.length * 100 / (rr.validLines.length + rr.invalidLines.length)
  def initializeFromCsv(
      validLinePercentRequired: Int
  ): Either[List[InvalidLine], Database] = {
    val airportsLines: ReadResult[Airport] =
      CSV.read("airports.csv", Airport.parseAirport)
    if (getValidPercent(airportsLines) < validLinePercentRequired)
      Left(airportsLines.invalidLines)
    else {

      val countriesLines: ReadResult[Country] =
        CSV.read("countries.csv", Country.parseCountry)
      if (getValidPercent(countriesLines) < validLinePercentRequired)
        Left(countriesLines.invalidLines)
      else {

        val runwaysLines: ReadResult[Runway] =
          CSV.read("runways.csv", Runway.parseRunway)
        if (getValidPercent(runwaysLines) < validLinePercentRequired)
          Left(runwaysLines.invalidLines)
        else
          Right(
            Database(
              airportsLines.validLines,
              countriesLines.validLines,
              runwaysLines.validLines
            )
          )
      }
    }
  }

}
