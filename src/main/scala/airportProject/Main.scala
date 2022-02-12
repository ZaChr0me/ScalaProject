package airportProject

import airportProject.model._
import airportProject.service.CSV

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

  //for now, and to go faster, main is used as manual testing, while the core app
  //isn't done, and the work on gui hasn't started

  val query_code: String = ""
  val query_name: String = ""
  //in consideration with ui, boolean indicating if code or name
  val code_or_name: Boolean = false

  val query_name_fuzzy: String = ""
}

def QueryWork(country_code_or_name: String, code_or_name: Boolean) = {
  code_or_name.match {
    case true  => {}
    case false => {}
  }
}
