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
    case "countries" => database.parseReportCountries()
    case "surface"   => database.parseReportSurface()
    case "latitude"  => database.parseReportLatitude()
  }
}

def queryCode(countryCode: String, database: Database) = {
  val result: List[(Airport, List[Runway])] =
    database.parseCountryCode(countryCode)
}

def queryName(countryName: String, database: Database) = {
  val result: List[(Airport, List[Runway])] =
    database.parseCountryName(countryName)
}


