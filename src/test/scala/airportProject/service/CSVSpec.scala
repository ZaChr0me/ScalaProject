package airportProject.service

import airportProject.model._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import airportProject.model.Airport

class CSVSpec extends AnyFlatSpec with Matchers:

  "CSV" should "read a valid csv" in {
    CSV.read("tests/airportsTest.csv", Airport.parseAirport).invalidLines.size must be(0)
    CSV
      .read("tests/airportsTest.csv", Airport.parseAirport)
      .validLines
      .size must be(3)
  }

  