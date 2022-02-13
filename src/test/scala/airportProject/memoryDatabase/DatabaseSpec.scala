package airportProject.memoryDatabase

import airportProject.model._
import airportProject.service._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class DatabaseSpec extends AnyFlatSpec with Matchers {

  "Database" should "Stop if the number of invalid lines is too high" in {
    Database
      .initializeFromCsv(
        100,
        "src/main/resources/airports.csv",
        "src/main/resources/countries.csv",
        "src/main/resources/runways.csv"
      )
      .isLeft must be(true)
  }

  "Database" should "Initialize successfully at least 60 percent of the 3 given csv" in {
    Database
      .initializeFromCsv(
        60,
        "src/main/resources/airports.csv",
        "src/main/resources/countries.csv",
        "src/main/resources/runways.csv"
      )
      .isLeft must be(false)
  }

  "Database" should "Initialize successfully 100 percent of the 3 test csv" in {
    Database
      .initializeFromCsv(
        100,
        "src/main/data/tests/airportsTest.csv",
        "src/main/data/tests/countriesTest.csv",
        "src/main/data/tests/runwaysTest.csv"
      )
      .isLeft must be(false)
  }

  "Database" should "Send every airport and runways of a country using his country code" in {
    val database = Database
      .initializeFromCsv(
        100,
        "src/main/data/tests/airportsTest.csv",
        "src/main/data/tests/countriesTest.csv",
        "src/main/data/tests/runwaysTest.csv"
      )
      .right
      .get

    database.parseCountryCode(IsoCountryType.orNone("US").get).size must be(3)
    database
      .parseCountryCode(IsoCountryType.orNone("US").get)
      .foreach((a, r) => r.size must be(1))
  }

  "Database" should "Send every airport and runways of a country using his country name" in {
    val database = Database
      .initializeFromCsv(
        100,
        "src/main/data/tests/airportsTest.csv",
        "src/main/data/tests/countriesTest.csv",
        "src/main/data/tests/runwaysTest.csv"
      )
      .right
      .get

    database
      .parseCountryName(NonEmptyString.orNone("United States").get)
      .size must be(3)
    database
      .parseCountryName(NonEmptyString.orNone("United States").get)
      .foreach((a, r) => r.size must be(1))
  }

  "Database" should "Give the first 10 and last 10 countries with parseReportCountries" in {
    val database = Database
      .initializeFromCsv(
        60,
        "src/main/resources/airports.csv",
        "src/main/resources/countries.csv",
        "src/main/resources/runways.csv"
      )
      .right
      .get

    database.parseReportCountries().size must be(20)
  }

  "Database" should "give the list of countries and their surface types with parseReportSurface" in {
    val database = Database
      .initializeFromCsv(
        100,
        "src/main/data/tests/airportsTest.csv",
        "src/main/data/tests/countriesTest.csv",
        "src/main/data/tests/runwaysTest.csv"
      )
      .right
      .get

    database.parseReportSurface().size must be(2)
    database.parseReportSurface().head._2.size must be(3)
  }

  "Database" should "give the list of latitudes and the number of time they appear with parseReportLatitude" in {
    val database = Database
      .initializeFromCsv(
        100,
        "src/main/data/tests/airportsTest.csv",
        "src/main/data/tests/countriesTest.csv",
        "src/main/data/tests/runwaysTest.csv"
      )
      .right
      .get

    database.parseReportLatitude().size must be(3)
    database.parseReportLatitude().foreach(data => data._2 must be(1))
  }
}
