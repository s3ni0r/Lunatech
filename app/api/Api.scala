package api
import javax.inject.Singleton

import Data.{AirportsByCountry, QueryResult, SurfaceByCountry}
import cats.effect.IO
import doobie.util.transactor.Transactor.Aux
import model.Queries
import utils.Formatter

trait Api extends Queries with Formatter {
  def getAirportRunwaysByCountryName(countryName: String)(implicit db: Aux[IO, Unit]): List[QueryResult]

  def getAirportRunwaysByCountryCode(countryCode: String)(implicit db: Aux[IO, Unit]): List[QueryResult]

  def getCountriesWithTopFlopTenNbrOfRunway()(implicit db: Aux[IO, Unit]): List[AirportsByCountry]

  def getRunwaysSurfacesByCountry()(implicit db: Aux[IO, Unit]): List[SurfaceByCountry]
}

@Singleton
class ApiImpl extends Api {
  override def getAirportRunwaysByCountryName(countryName: String)(implicit db: Aux[IO, Unit]): List[QueryResult] =
    formatAirportRunwaysByCountry(airportRunwaysByCountryName(countryName))

  override def getAirportRunwaysByCountryCode(countryCode: String)(implicit db: Aux[IO, Unit]): List[QueryResult] =
    formatAirportRunwaysByCountry(airportRunwaysByCountryCode(countryCode))

  override def getCountriesWithTopFlopTenNbrOfRunway()(implicit db: Aux[IO, Unit]): List[AirportsByCountry] =
    formatCountriesWithTopFlopTenNbrOfRunway(countriesWithTopFlopTenNbrOfRunway)

  override def getRunwaysSurfacesByCountry()(implicit db: Aux[IO, Unit]): List[SurfaceByCountry] =
    formatRunwaysSurfacesByCountry(runwaysSurfacesByCountry)
}
