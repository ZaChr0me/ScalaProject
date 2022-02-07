package airportProject.memoryDatabase

import airportProject.model._
import airportProject.service._
import scala.io.Source

class Database (
    val airports: List[Airport],
    val countries: List[Country],
    val runways: List[Runway])



object Database {
    def initializeFromCsv(): Database = {
        Database(CSV.read("airports.csv", Airport.parseAirport).lines.toList,
        CSV.read("countries.csv", Country.parseCountry).lines.toList, 
        CSV.read("runways.csv", Runway.parseRunway).lines.toList)
    }

}