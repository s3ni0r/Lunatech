package utils

import models.Data._

trait Formatter {
  def formatAirportRunwaysByCountry(rows: List[QueryParser]): List[QueryResult] = {
    rows
      .groupBy(_.country)
      .mapValues {
        _.groupBy(_.airport)
          .mapValues(_.map(_.runway))
      }
      .map {
        case (k: Country, v: Map[Airport, List[Runway]]) => {
          val airports = v.map {
            case (k: Airport, v: List[Runway]) => AirportRunways(k.name, v)
          }
          QueryResult(k.name, k.code, airports.toList)
        }
      }
      .toList
  }

  def formatCountriesWithTopFlopTenNbrOfRunway(rows: List[AirportsByCountry]): List[AirportsByCountry] =
    rows.sortBy(a => (-a.nbrOfAirports, a.name)).take(10) ++ rows.sortBy(a => (a.nbrOfAirports, a.name)).take(10)

  def formatRunwaysSurfacesByCountry(rows: List[SurfaceByCountryResponse]): List[SurfaceByCountry] =
    rows
      .groupBy(_.country)
      .mapValues(_.map(_.surface.getOrElse("-")))
      .map {
        case (k, v) => SurfaceByCountry(k, v)
      }
      .toList
}
