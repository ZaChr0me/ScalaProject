package airportProject.model

import airportProject.service._

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class ParserSpec extends AnyFlatSpec with Matchers:

  "Airports" should "Parse a valid airport" in {
    val validAirport = Array(
      "6523",
      "00A",
      "heliport",
      "Total Rf Heliport",
      "40.07080078125",
      "-74.93360137939453",
      "11",
      "NA",
      "US",
      "US-PA",
      "Bensalem",
      "no",
      "00A",
      "",
      "00A",
      "",
      "",
      ""
    )
    val validAirportParsed = Airport(
      6523,
      NonEmptyString.orNone("00A").get,
      AirportType.valueOf("heliport"),
      NonEmptyString.orNone("Total Rf Heliport").get,
      ContinentType.valueOf("NA"),
      IsoCountryType.orNone("US").get,
      IsoRegionType.orNone("US-PA").get,
      NonEmptyString.orNone("Bensalem").get,
      Option(false),
      Option(NonEmptyString.orNone("00A").get),
      None,
      Option(NonEmptyString.orNone("00A").get),
      None,
      None,
      None
    )
    Airport.parseAirport(validAirport).isRight must be(true)
    Airport.parseAirport(validAirport).contains(validAirportParsed)
  }

  it should "Not parse an invalid airport" in {
    val invalidAirport = Array("This, won't, work")
    Airport.parseAirport(invalidAirport).isLeft must be(true)
  }

  "Countries" should "Parse a valid country" in {
    val validCountry = Array(
      "302612",
      "\"ZW\",\"Zimbabwe\",\"AF\",\"http://en.wikipedia.org/wiki/Zimbabwe\","
    )
    val validCountryParsed = Country(
      302612,
      IsoCountryType.orNone("ZW").get,
      NonEmptyString.orNone("Zimbabwe").get,
      ContinentType.valueOf("AF"),
      Option(
        NonEmptyString.orNone("http://en.wikipedia.org/wiki/Zimbabwe").get
      ),
      None
    )
    Country.parseCountry(validCountry).isRight must be(true)
    Country.parseCountry(validCountry).contains(validCountryParsed)
  }

  it should "Not parse an invalid country" in {
    val invalidCountry = Array("This", "won't", "work")
    Country.parseCountry(invalidCountry).isLeft must be(true)
  }

  "Runways" should "Parse a valid runway" in {
    val validRunway = Array(
      "253744,6802,\"04W\",2751,75,\"ASPH-G\",1,0,\"06\",46.0213,-92.9001,1021,66,190,\"24\",46.0244,-92.8902,1009,246,394"
    )
    val validRunwayParsed = Runway(
      253744,
      6802,
      NonEmptyString.orNone("04W").get,
      NonEmptyString.orNone("ASPH-G").get,
      AirportLeIndentType.orNone("06").get,
      Option(2751),
      Option(75),
      Option(true),
      Option(false),
      Option("46.0213"),
      Option("-92.9001"),
      Option(1021),
      Option("66"),
      Option(190),
      Option("24"),
      Option("46.0244"),
      Option("-92.8902"),
      Option(1009),
      Option("246"),
      Option(394)
    )
    Runway.parseRunway(validRunway).isRight must be(true)
    Runway.parseRunway(validRunway).contains(validRunwayParsed)
  }

  it should "Not parse an invalid runway" in {
    val invalidRunway = Array("This", "won't", "work")
    Runway.parseRunway(invalidRunway).isLeft must be(true)
  }
