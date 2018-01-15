package test.utils

import models.Data._
import models.Queries
import tools.DbConnection
import utils.Formatter

import scala.util.Random

class FormatterTest extends DbConnection with Queries with Formatter {
  withInMemoryDb { implicit db =>
    "formatAirportRunwaysByCountry" - {
      "When querying a country" - {

        val targetCode = "SY"
        val targetName = "Syria"
        val targetNbrOfAirports = 23
        val targetRandomAirport = "Dumayr Air Base"
        val targetNbrOfRunways = 2

        s"By Country Code $targetCode" - {
          formatAirportRunwaysByCountry(airportRunwaysByCountryCode(targetCode)).map { r =>
            "should be instance of QueryResult" in {
              assert(r.isInstanceOf[QueryResult])
            }

            s"should have the correct countryCode $targetCode" in {
              assert(r.countryCode === targetCode)
            }

            s"should have $targetNbrOfAirports airports" in {
              assert(r.airports.length === targetNbrOfAirports)
            }

            s"should contain $targetRandomAirport airport" in {
              assert(r.airports.exists(a => a.airportName === targetRandomAirport) === true)
            }

            s"should have $targetNbrOfRunways runways" in {
              r.airports.find(a => a.airportName === targetRandomAirport).map { ar =>
                assert(ar.runways.length === targetNbrOfRunways)
              }
            }
          }
        }

        s"By Country Name $targetName" - {
          formatAirportRunwaysByCountry(airportRunwaysByCountryName(targetName)).map { r =>
            s"should have the correct countryCode $targetName" in {
              assert(r.countryName === targetName)
            }
          }
        }
      }
    }

    "formatCountriesWithTopFlopTenNbrOfRunway" - {
      val sortedTopFlopCountries = List(
        AirportsByCountry("C6", 100),
        AirportsByCountry("C8", 99),
        AirportsByCountry("C2", 98),
        AirportsByCountry("C4", 97),
        AirportsByCountry("C11", 96),
        AirportsByCountry("C13", 95),
        AirportsByCountry("C14", 94),
        AirportsByCountry("C15", 93),
        AirportsByCountry("C16", 92),
        AirportsByCountry("C17", 91),
        AirportsByCountry("C1", 1),
        AirportsByCountry("C5", 2),
        AirportsByCountry("C7", 3),
        AirportsByCountry("C9", 4),
        AirportsByCountry("C10", 5),
        AirportsByCountry("C12", 6),
        AirportsByCountry("C3", 7),
        AirportsByCountry("C11", 8),
        AirportsByCountry("C13", 9),
        AirportsByCountry("C14", 10)
      )

      val unsortedTopFlopCountries = Random.shuffle(sortedTopFlopCountries) ++ List(
        AirportsByCountry("C18", 91),
        AirportsByCountry("C19", 90),
        AirportsByCountry("C33", 11),
        AirportsByCountry("C88", 12)
      )


      "sorted and unsorted top flop countries should not match" in {
        assert(sortedTopFlopCountries !== unsortedTopFlopCountries)
      }


      "should return 20 countries when list has more than 20 elements" in {
        assert(formatCountriesWithTopFlopTenNbrOfRunway(unsortedTopFlopCountries).length === 20)
      }

      "should return sorted top flop countries when countries are >= 20" in {
        assert(formatCountriesWithTopFlopTenNbrOfRunway(unsortedTopFlopCountries) === sortedTopFlopCountries)
      }
    }

    "formatRunwaysSurfacesByCountry" - {
      val surfacesByCountry = formatRunwaysSurfacesByCountry(runwaysSurfacesByCountry)
      val selectedCountry = "Syria"
      val numberOfSurfaces = 6
      "should be instance of SurfaceByCountry " in {
        assert(surfacesByCountry.isInstanceOf[List[SurfaceByCountry]])
      }

      s"should have $numberOfSurfaces surfaces for country $selectedCountry" in {
        assert(surfacesByCountry.find(_.country === selectedCountry).map( s => s.surfaces.length) === Some(numberOfSurfaces))
      }
    }
  }
}
