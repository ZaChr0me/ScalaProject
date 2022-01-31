package airport.model

import scala.io.Source

class Database {
    airports: List[Airport],
    countries: List[Country],
    runways: List[Runway]
}

def initializeFromCsv(): Database = {
    Database(
        CSV.read[Airport]("resources/airports.csv", Airport.parseAirport).lines,
        CSV.read[Country]("resources/countries.csv", Country.parseCountry).lines,
        CSV.read[Runway]("resources/runways.csv", Runway.parseRunway).lines
    )
}