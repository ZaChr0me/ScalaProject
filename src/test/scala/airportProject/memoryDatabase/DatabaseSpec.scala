package airportProject.memoryDatabase

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class DatabaseSpec extends AnyFlatSpec with Matchers {

  "Database" should "Stop if the number of invalid lines is too high" in {
    Database
      .initializeFromCsv(100, "airports.csv", "countries.csv", "runways.csv")
      .isLeft must be(true)
  }

}
