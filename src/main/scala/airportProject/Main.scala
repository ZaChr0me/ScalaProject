package airportProject

import airportProject.model._
import airportProject.service._
import airportProject.memoryDatabase._

import scala.collection.MapView
import scala.io.BufferedSource
import scala.collection.mutable
import scala.collection.parallel.CollectionConverters._
import scala.collection.parallel.ParSeq
import scala.collection.mutable.ListBuffer

def Initialize() = {}

def main(args: Array[String]): Unit = {
  CSV.read("test", Country.parseCountry)
  printf("project console entry point. TEMPORARY.")

  //input user choice between query and reports
  val reportOrQuery = false

  val reportType = ""
  //for now, and to go faster, main is used as manual testing, while the core app
  //isn't done, and the work on gui hasn't started

  val queryCodeOrName: String = ""
  //in consideration with ui, boolean indicating if code or name
  val codeOrName: Boolean = false

  val query_name_fuzzy: String = ""

  reportOrQuery.match {
    case true  => reportWork(database, reportType)
    case false => queryWork(queryCodeOrName, codeOrName, database)
  }

}

def queryWork(
    countryCodeOrName: String,
    codeOrName: Boolean,
    database: Database
) = {
  codeOrName.match {
    case true  => { queryCode(countryCodeOrName, database) }
    case false => { queryName(countryCodeOrName, database) }
  }

}

def reportWork(database: Database, reportType: String) = {
  reportType.match {
    case "countries" => parseReportCountries(database)
    case "surface"   => parseReportSurface(database)
    case "latitude"  => parseReportLatitude(database)
  }
}

def queryCode(countryCode: String, database: Database) = {
  val result: List[(Airport, List[Runway])] =
    parseCountryCode(database, countryCode)
}

def queryName(countryName: String, database: Database) = {
  val result: List[(Airport, List[Runway])] =
    parseCountryName(database, countryName)
}

def parseCountryCode(
    database: Database,
    countryCode: String
): List[(Airport, List[Runway])] = database.airports
  .filter(airport => { airport.isoCountry == countryCode })
  .map(airport =>
    (
      airport,
      database.runways.filter(x => {
        x.airportIdent == airport.ident && x.airportRef == airport.id
      })
    )
  )

def parseCountryName(
    database: Database,
    countryName: String
): List[(Airport, List[Runway])] = database.airports
  .filter(airport => { airport.name == countryName })
  .map(airport =>
    (
      airport,
      database.runways.filter { runway =>
        (runway.airportRef == airport.id && runway.airportIdent == airport.ident)
      }
    )
  )

def parseReportCountries(database: Database): List[(Country, Int)] =
  database.countries
    .map(country =>
      (
        country,
        database.airports
          .filter(airport => (airport.isoCountry == country.code))
          .size
      )
    )
    .sortWith(_._2 > _._2)
    .filter((x, y) => y < 10 && y > (database.countries.size - 10))

def parseReportSurface(database: Database): List[(Country, List[String])] =
  database.countries
    .map(country =>
      (
        country,
        database.runways
          .filter(runway =>
            database.airports
              .filter(airport => (airport.isoCountry == country.code))
              .contains(runway.airportIdent)
          )
          .map(_.surface)
          .map(_.toUpperCase)
          .distinct
      )
    )

def parseReportLatitude(database: Database): List[(String, Int)] =
  database.runways
    .map(runway =>
      (
        String(runway.leIdent),
        database.runways.filter(x => x.leIdent == runway.leIdent).size
      )
    )
    .distinct
