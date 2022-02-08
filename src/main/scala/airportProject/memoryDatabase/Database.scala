package airportProject.memoryDatabase

import airportProject.model._
import airportProject.service._
import scala.io.Source

class Database (
    val airports: List[Airport],
    val countries: List[Country],
    val runways: List[Runway]){

        def parseCountryCode(countryCode: String): List[(Airport, List[Runway])] = airports
        .filter(airport => { airport.isoCountry == countryCode })
        .map(airport =>
            (
            airport,
            runways.filter(x => {
                x.airportIdent == airport.ident && x.airportRef == airport.id
            })
            )
        )

        def parseCountryName(countryName: String): List[(Airport, List[Runway])] = airports
        .filter(airport => { airport.name == countryName })
        .map(airport =>
            (
            airport,
            runways.filter { runway =>
                (runway.airportRef == airport.id && runway.airportIdent == airport.ident)
            }
            )
        )

        def parseReportCountries(): List[(Country, Int)] =
        countries
            .map(country =>
            (
                country,
                airports
                .filter(airport => (airport.isoCountry == country.code))
                .size
            )
            )
            .sortWith(_._2 > _._2)
            .filter((x, y) => y < 10 && y > (countries.size - 10))

        def parseReportSurface(): List[(Country, List[String])] =
        countries
            .map(country =>
            (
                country,
                runways
                .filter(runway =>
                    airports
                    .filter(airport => (airport.isoCountry == country.code))
                    .contains(runway.airportIdent)
                )
                .map(_.surface)
                .map(_.toUpperCase)
                .distinct
            )
            )

        def parseReportLatitude(): List[(String, Int)] =
        runways
            .map(runway =>
            (
                String(runway.leIdent),
                runways.filter(x => x.leIdent == runway.leIdent).size
            )
            )
            .distinct
    }



object Database {
    def initializeFromCsv(): Database = {
        Database(CSV.read("airports.csv", Airport.parseAirport).lines.toList,
        CSV.read("countries.csv", Country.parseCountry).lines.toList, 
        CSV.read("runways.csv", Runway.parseRunway).lines.toList)
    }

    

}