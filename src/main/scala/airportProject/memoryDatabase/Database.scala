package airportProject.memoryDatabase

import airportProject.model._
import airportProject.service._
import scala.io.Source

class Database (
    airports: List[Airport],
    countries: List[Country],
    runways: List[Runway]
)

object Database:
    def initializeFromCsv(): Database = {
        Database(
            CSV.read[Airport]("resources/airports.csv", Airport.parseAirport).lines,
            CSV.read[Country]("resources/countries.csv", Country.parseCountry).lines,
            CSV.read[Runway]("resources/runways.csv", Runway.parseRunway).lines
        )
    }

    def parseCountryCode(countryCode: String): List[(Airport, List[Runway])] = airports
    airports.dropWhile(airport=>{airport.isoCountry!=countryCode})
    .map(airport=>(airport,runways.filter{x => (x.airportIdent == airport.ident && x.airportRef==airport.id)}))

    def parseCountryName(countryName: String): List[(Airport, List[Runway])] = airports
    .dropWhile(airport => {airport.name != countryName})
    .map(airport => (airport, runways.filter{runway => (runway.airportRef == airport.id && runway.airportIdent == airport.ident)}))
    
    def parseReportCountries(): Option[List[(Country, Int)]] = countries.match {
        case (countries.count >= 20) => countries
        .map(country => (country, airports.filter(airport => (airport.isoCountry == country.code)).count))
        .sortWith((_, _ > _))
        .filter(_._2 < 10 && _._2 > (countries.count - 10)).map(_._1)
        case _ => None
    }
    
    def parseReportSurface(): List[(Country, List[String])] = countries
    .map(country => (country, runways.filter(runway => airports.filter(airport => (airport.isoCountry == country.code)).Ident.contains(runway.airportIdent)).surface.map(_.toUpperCase).distinct))

    def parseReportLatitude(): List[(String, Int)] = runways
    .map(runway => (String(runway.leIdent)), runway.filter(x => x.leIdent == runway.leIdent).count)
    .distinct