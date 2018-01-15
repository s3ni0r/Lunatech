package models

import doobie.Query0
import cats.effect.IO
import doobie.implicits._
import doobie.util.transactor.Transactor.Aux
import Data._

trait Queries {
  def executeQuery[T](query: Query0[T])(implicit db: Aux[IO, Unit]): List[T] =
    query.list.transact(db).unsafeRunSync

  def airportRunwaysByCountryName(countryName: String)(implicit db: Aux[IO, Unit]) = {

    val query: Query0[QueryParser] = sql"""SELECT *
        FROM country c JOIN airport a ON c.code = a.iso_country JOIN runway r ON a.id = r.airport_ref
        WHERE LOWER(c.name) LIKE LOWER($countryName) || '%'
    """.query[QueryParser]
    executeQuery(query)
  }

  def airportRunwaysByCountryCode(countryCode: String)(implicit db: Aux[IO, Unit]) = {

    val query: Query0[QueryParser] = sql"""SELECT *
        FROM country c JOIN airport a ON c.code = a.iso_country JOIN runway r ON a.id = r.airport_ref
        WHERE LOWER(c.code) LIKE LOWER($countryCode) || '%'
    """.query[QueryParser]

    executeQuery(query)
  }

  def countriesWithTopFlopTenNbrOfRunway()(implicit db: Aux[IO, Unit]): List[AirportsByCountry] = {
    val query: Query0[AirportsByCountry] = sql"""SELECT c.name, count(*)
        FROM country c JOIN airport a ON c.code = a.iso_country
        GROUP BY c.name
    """.query[AirportsByCountry]

    executeQuery(query)
  }

  def runwaysSurfacesByCountry()(implicit db: Aux[IO, Unit]): List[SurfaceByCountryResponse] = {
    val query: Query0[SurfaceByCountryResponse] = sql"""SELECT c.name, r.surface
        FROM country c JOIN airport a ON c.code = a.iso_country JOIN runway r ON a.id = r.airport_ref
        GROUP BY c.name, r.surface
    """.query[SurfaceByCountryResponse]

    executeQuery(query)
  }
}
