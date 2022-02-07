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

//implementer la scala 3 functionality that allows dealing with null (check at compile time that there are no null)

//fuzzy matching by levenshtein distance calculator algorithm, found here https://rosettacode.org/wiki/Levenshtein_distance#Scala
//needs to understand this better, ask teacher
def levenshtein(s1: String, s2: String): Int = {
  val memoizedCosts = mutable.Map[(Int, Int), Int]()

  def lev: ((Int, Int)) => Int = { case (k1, k2) =>
    memoizedCosts.getOrElseUpdate(
      (k1, k2),
      (k1, k2) match {
        case (i, 0) => i
        case (0, j) => j
        case (i, j) =>
          //what the hell is this doing??????
          ParSeq(
            1 + lev((i - 1, j)),
            1 + lev((i, j - 1)),
            lev((i - 1, j - 1))
              + (if (s1(i - 1) != s2(j - 1)) 1 else 0)
          ).min
      }
    )
  }

  lev((s1.length, s2.length))
  //why is this needed?
  memoizedCosts(s1.length, s2.length)
}

def cleanUpLanguage(
    text: String,
    languageList: List[String] = List(
      "ook",
      "lolcode",
      "brainfuck",
      "intercal",
      "arnoldc",
      "c",
      "c++",
      "c#"
    )
): String = {
  //fuzzy matching can result in incoherences
  def recursiveMatch(
      t: String,
      list: List[String],
      acc: (String, Int) = ("", Int.MaxValue)
  ): String = list.match {
    case Nil                                   => acc._1
    case s :: tail if (levenshtein(s, t) == 0) => s
    case s :: tail if (levenshtein(s, t) < acc._2) =>
      recursiveMatch(t, tail, (s, levenshtein(s, t)))
    case _ :: tail => recursiveMatch(t, tail, acc)
  }
  recursiveMatch(text, languageList.sortBy(_.length)(Ordering[Int].reverse))
}

def languageVoting(voteList: List[(Int, String)]): List[(String, Int)] = {
  def recursiveMatch(
      vL: List[(Int, String)],
      acc: mutable.Map[String, Int] = mutable.Map[String, Int]()
  ): List[(String, Int)] = vL.match {
    case Nil => acc.toList
    case (count, language) :: tail if (acc.contains(language)) =>
      recursiveMatch(tail, acc += ((language, (acc.get(language).get + count))))
    case (count, language) :: tail =>
      recursiveMatch(tail, acc += ((language, count)))
  }
  recursiveMatch(
    voteList.map((count, language) => (count, cleanUpLanguage(language)))
  )
}

/*
  //WORK FOR TODAY, IGNORE FOR
  val givenL: List[(Int, String)] = List(
    (1, "ook!"),
    (1, "lolcode"),
    (3, "intercal"),
    (1, "ook ook"),
    (1, "brainfuck"),
    (2, "ArnoldC")
  )
  val l: List[(String, Int)] = languageVoting(givenL)
  l.foreach((l, c) => printf("language :" + l + " has :" + c + " votes."))

 */

def initialize(): Database = Database.initializeFromCsv()

def main(args: Array[String]): Unit = {
  val database = initialize()
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
