package airportProject.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers




class ParserSpec extends AnyFlatSpec with Matchers:
    

    "Airports" should "Parse a valid airport" in {
        validAirport = List(6523,"00A","heliport","Total Rf Heliport",40.07080078125,-74.93360137939453,11,"NA","US","US-PA","Bensalem","no","00A",Nil,"00A",Nil,Nil,Nil)
        validAirportParsed = Airport(6523,"00A","heliport","Total Rf Heliport",40.07080078125,-74.93360137939453,11,"NA","US","US-PA","Bensalem","no","00A","","00A","","","")
        Airports.parseAirport(validAirport).get must be(validAiportParsed)
    }

    it should "Not parse an invalid airport" in {
        invalidAirport = List("This", "won't", "work")
        Airports.parseAirport(invalidAirport).get must be None
    }

    "Countries" should "Parse a valid country" in {
        validCountry = List(302612,"ZW","Zimbabwe","AF","http://en.wikipedia.org/wiki/Zimbabwe",Nil)
        validCountryParsed = Country(302612,"ZW","Zimbabwe","AF","http://en.wikipedia.org/wiki/Zimbabwe","")
        Countries.parseCountry(validCountry).get must be(validCountryParsed)
    }

    it should "Not parse an invalid country" in {
        invalidCountry = List("This", "won't", "work")
        Countries.parseCountry(invalidCountry).get must be None
    }

    "Runways" should "Parse a valid runway" in {
        validRunway = List(253744,6802,"04W",2751,75,"ASPH-G",1,0,"06",46.0213,-92.9001,1021,66,190,"24",46.0244,-92.8902,1009,246,394)
        validRunwayParsed = Runway(253744,6802,"04W",2751,75,"ASPH-G",1,0,"06",46.0213,-92.9001,1021,66,190,"24",46.0244,-92.8902,1009,246,394)
        Runways.parseRunway(validRunway).get must be(validRunwayParsed)
    }

    it should "Not parse an invalid runway" in {
        invalidRunway = List("This", "won't", "work")
        Runways.parseRunway(invalidRunway).get must be None
    }