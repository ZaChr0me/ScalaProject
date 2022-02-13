package airportProject
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.paint.Color._
import scalafx.scene.paint._
import scalafx.scene.text.Text
import scalafx.scene.control._
import scalafx.scene.layout._
import scalafx.event.EventHandler
import scalafx.scene.control.TabPane.TabClosingPolicy

import airportProject.model._
import airportProject.service._
import airportProject.memoryDatabase._
import airportProject.uiService._

enum ReportType {
  case Countries, Surfaces, Latitude
}

enum ReportOrQuery {
  case Report, Query
}

def initialize(): Either[List[InvalidLine], Database] =
  Database.initializeFromCsv(60, "airports.csv", "countries.csv", "runways.csv")

object ScalaFXHelloWorld extends JFXApp3 {
  override def start(): Unit = {

    val database = initialize
    if (database.isLeft) {
      database.left.get.foreach(line => {
        if (InvalidLine.getElement(line) != "")
          printf(InvalidLine.print(line) + "\n")
      })
    } else if (database.isRight) {

      val queryPane = QueryPane.getContent

      queryPane.queryButton.onAction = ((event: ActionEvent) => {
        val query = queryWork(
          queryPane.queryText.getText,
          TypeOfQuery.valueOf(
            queryPane.queryType.getValue
          ), //if (queryPane.queryType.getValue == "Name") TypeOfQuery.Name
          //else TypeOfQuery.Code,
          database.right.get,
          false
        )

        if (query.isDefined) {
          queryPane.setContent(query.get)
        }
      })

      val reportPane = ReportPane.getContent

      reportPane.reportTopCountriesQuery.onAction = ((event: ActionEvent) => {
        val report = reportWork(database.right.get, ReportType.Countries)
        reportPane.setTopBottomCountries(
          report.asInstanceOf[List[(Country, Int)]]
        )
      })
      reportPane.reportTypesofSurfaceQuery.onAction = ((event: ActionEvent) => {
        val report = reportWork(database.right.get, ReportType.Surfaces)
        reportPane.setAllTypesOfSurface(
          report.asInstanceOf[List[(Country, List[String])]]
        )
      })
      reportPane.reportTopCommonLatitudeQuery.onAction =
        ((event: ActionEvent) => {
          val report = reportWork(database.right.get, ReportType.Latitude)
          reportPane.setTopCommonLatitude(
            report.asInstanceOf[List[(String, Int)]]
          )
        })

      stage = new JFXApp3.PrimaryStage {
        title = "Airport Project"

        scene = new Scene(720, 688) {
          root = new BorderPane {
            id = "mainPagePanel"
            center = new TabPane {
              vgrow = Priority.Never
              tabs = Seq(
                new Tab {
                  text = "Queries"
                  content = queryPane.panel
                },
                new Tab {
                  text = "Reports"
                  content = reportPane.panel
                }
              )
              tabClosingPolicy = TabClosingPolicy.Unavailable
            }
          }
        }
      }
    }
  }
}
//main commented to ease starting the application. Kept to run simple manual tests
/*def main(args: Array[String]): Unit = {

  val database = initialize
  if (database.isLeft) {
    database.left.get.foreach(line => {
      if (InvalidLine.getElement(line) != "")
        printf(InvalidLine.print(line) + "\n")
    })
  } else if (database.isRight) {

    val reportOrQuery: ReportOrQuery = ReportOrQuery.Report
    val reportType = ReportType.Latitude
    val queryCodeOrName: String = "France"
    val codeOrName: TypeOfQuery = TypeOfQuery.Name
    val query_name_fuzzy: String = ""

    reportOrQuery.match {
      case ReportOrQuery.Report => reportWork(database.right.get, reportType)
      case ReportOrQuery.Query =>
        queryWork(queryCodeOrName, codeOrName, database.right.get)
    }

  }

}*/

def queryWork(
    countryCodeOrName: String,
    codeOrName: TypeOfQuery,
    database: Database,
    printToConsole: Boolean = true
): Option[List[(Airport, List[Runway])]] = {
  codeOrName.match {
    case TypeOfQuery.Code => {
      val ict: Option[IsoCountryType] = IsoCountryType.orNone(countryCodeOrName)
      if (ict.isDefined) {
        val res =
          queryCode(ict.get, database)

        if (printToConsole) {
          res.foreach((ap, l) => {
            val allRunways = l.map(runway => runway.id).mkString(",")
            printf(
              "Airport : " + ap.name.toString + ", " + l.length.toString + " Runways : " + allRunways + "\n"
            )
          })
        }
        Some(res)
      } else {
        printf("problem")
        None
      } //TODO
    }
    case TypeOfQuery.Name => {
      val nes: Option[NonEmptyString] = NonEmptyString.orNone(countryCodeOrName)
      if (nes.isDefined) {
        val res =
          queryName(nes.get, database)
        if (printToConsole) {
          res.foreach((ap, l: List[Runway]) => {
            val allRunways = l.map(runway => runway.id).mkString(",")
            printf(
              "Airport : " + ap.name.toString + ", " + l.length.toString + " Runways : " + allRunways + "\n"
            )
          })
        }
        Some(res)
      } else {
        printf("problem")
        None
      } //TODO
    }
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

def reportWork(database: Database, reportType: ReportType) = {

  reportType.match {
    case ReportType.Countries =>
      val res = database
        .parseReportCountries()

      res.zipWithIndex.foreach((countryValueTuple, i) =>
        printf(
          i.toString + " ranked Country " + countryValueTuple._1.name.toString + " has " + countryValueTuple._2 + " runways\n"
        )
      )
      res
    case ReportType.Surfaces =>
      val res = database
        .parseReportSurface()
      res.foreach((c, subl) =>
        printf(
          "Country " + c.name.toString + " has the following types of runways: " + subl
            .mkString(",")
        )
      )
      res
    case ReportType.Latitude =>
      val res = database
        .parseReportLatitude()

      res.zipWithIndex.foreach((latitude, i) =>
        printf(
          i.toString + " ranked latitude " + latitude._1 + " is used " + latitude._2.toString + " times\n"
        )
      )
      res
  }
}
