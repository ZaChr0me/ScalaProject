package airportProject

import airportProject.model._
import airportProject.service._
import airportProject.memoryDatabase._

enum ReportType {
  case Countries, Surfaces, Latitude
}
enum CodeOrName {
  case Code, Name
}
enum ReportOrQuery {
  case Report, Query
}

def initialize(): Either[List[InvalidLine], Database] =
  Database.initializeFromCsv(60)

def main(args: Array[String]): Unit = {
  val database = initialize()
  if (database.isLeft) {
    database.left.get.foreach(line => {
      if (InvalidLine.getElement(line) != "")
        printf(InvalidLine.print(line) + "\n")
    })
  } else if (database.isRight) {

    //input user choice between query and reports
    val reportOrQuery: ReportOrQuery = ReportOrQuery.Report

    val reportType = ReportType.Latitude
    //for now, and to go faster, main is used as manual testing, while the core app
    //isn't done, and the work on gui hasn't started

    val queryCodeOrName: String = "France"
    //in consideration with ui, boolean indicating if code or name
    val codeOrName: CodeOrName = CodeOrName.Name

    val query_name_fuzzy: String = ""

    reportOrQuery.match {
      case ReportOrQuery.Report => reportWork(database.right.get, reportType)
      case ReportOrQuery.Query =>
        queryWork(queryCodeOrName, codeOrName, database.right.get)
    }

  }

}

def queryWork(
    countryCodeOrName: String,
    codeOrName: CodeOrName,
    database: Database
) = {
  codeOrName.match {
    case CodeOrName.Code => {
      val ict: Option[IsoCountryType] = IsoCountryType.orNone(countryCodeOrName)
      if (ict.isDefined) {
        val res =
          queryCode(ict.get, database)

        res.foreach((ap, l) => {
          val allRunways = l.map(runway => runway.id).mkString(",")
          printf(
            "Airport : " + ap.name.toString + ", " + l.length.toString + " Runways : " + allRunways + "\n"
          )
        })
      } else printf("problem") //TODO
    }
    case CodeOrName.Name => {
      val nes: Option[NonEmptyString] = NonEmptyString.orNone(countryCodeOrName)
      if (nes.isDefined) {
        val res =
          queryName(nes.get, database)
        res.foreach((ap, l: List[Runway]) => {
          val allRunways = l.map(runway => runway.id).mkString(",")
          printf(
            "Airport : " + ap.name.toString + ", " + l.length.toString + " Runways : " + allRunways + "\n"
          )
        })
      } else printf("problem") //TODO
    }
  }

}

def reportWork(database: Database, reportType: ReportType) = {

  reportType.match {
    case ReportType.Countries => database.parseReportCountries()
    case ReportType.Surfaces  => database.parseReportSurface()
    case ReportType.Latitude  => database.parseReportLatitude()
  }.match {
    case l: List[(Country, Int)] =>
      printf("\nrunning here\n")
      printf(l.length.toString)
      l.zipWithIndex.foreach((countryValueTuple, i) =>
        printf(
          i.toString + " ranked Country " + countryValueTuple._1.name.toString + " has " + countryValueTuple._2 + " runways"
        )
      )
    case l: List[(Country, List[String])] =>
      l.foreach((c, subl) =>
        printf(
          "Country " + c.name.toString + " has the following types of runways: " + subl
            .mkString(",")
        )
      )
    case l: List[(String, Int)] =>
      l.zipWithIndex.foreach((latitude, i) =>
        printf(
          i.toString + " ranked latitude " + latitude._1 + " is used " + latitude._2.toString + " times"
        )
      )
  }
}

def queryCode(
    countryCode: IsoCountryType,
    database: Database
): List[(Airport, List[Runway])] = {
  database.parseCountryCode(countryCode)
}

def queryName(
    countryName: NonEmptyString,
    database: Database
): List[(Airport, List[Runway])] = {
  database.parseCountryName(countryName)
}
