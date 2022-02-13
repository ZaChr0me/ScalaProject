package airportProject.service

import airportProject.model._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import airportProject.model.Airport

class CSVSpec extends AnyFlatSpec with Matchers:

  "CSV" should "read a valid airport csv" in {
    CSV
      .read("src/main/data/tests/airportsTest.csv", Airport.parseAirport)
      .invalidLines
      .size must be(0)
    CSV
      .read("src/main/data/tests/airportsTest.csv", Airport.parseAirport)
      .validLines
      .size must be(3)
  }

  "CSV" should "read a valid country csv" in {
    CSV
      .read("src/main/data/tests/countriesTest.csv", Country.parseCountry)
      .invalidLines
      .size must be(0)
    CSV
      .read("src/main/data/tests/countriesTest.csv", Country.parseCountry)
      .validLines
      .size must be(2)
  }

  "CSV" should "read a valid runway csv" in {
    CSV
      .read("src/main/data/tests/runwaysTest.csv", Runway.parseRunway)
      .invalidLines
      .size must be(0)
    CSV
      .read("src/main/data/tests/runwaysTest.csv", Runway.parseRunway)
      .validLines
      .size must be(3)
  }
