package airportProject.memoryDatabase

import airportProject.model._
import airportProject.service._
import scala.io.Source

class Database (
    airports: Array[Airport],
    countries: Array[Country],
    runways: Array[Runway]
)

object Database:
    def initializeFromCsv(): Database = {
        Database(
            CSV.read[Airport]("resources/airports.csv", Airport.parseAirport).lines,
            CSV.read[Country]("resources/countries.csv", Country.parseCountry).lines,
            CSV.read[Runway]("resources/runways.csv", Runway.parseRunway).lines
        )
    }

    def parseCountryCodeAirports(countryCode: String): Array[Airport] = airports.dropWhile(airport => {airport.iso_country != countryCode})
    
    def parseCountryCodeRunways(countryCode: String): Array[Runway] = parseCountryCodeAirports(countryCode)