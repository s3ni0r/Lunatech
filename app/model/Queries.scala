package model

import Data._
import doobie._
import cats.effect.IO
import doobie.implicits._
import doobie.util.transactor.Transactor

trait Queries {

  def executeQuery[T](query: Query0[T])(implicit db: Transactor.Aux[IO, Unit]): List[T] = {
    query.list.transact(db).unsafeRunSync
  }

  def RunQueryAndFormat(query: doobie.Query0[QueryParser])(implicit db: Transactor.Aux[IO, Unit]) = {
    executeQuery(query)
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
  }

  def getAirportRunwaysByCountryName(countryName: String)(implicit db: Transactor.Aux[IO, Unit]) = {

    val query: Query0[QueryParser] = sql"""SELECT *
        FROM country c JOIN airport a ON c.code = a.iso_country JOIN runway r ON a.id = r.airport_ref
        WHERE c.name like $countryName|| '%'
    """.query[QueryParser]

    RunQueryAndFormat(query)
  }

  def getAirportRunwaysByCountryCode(countryCode: String)(implicit db: Transactor.Aux[IO, Unit]) = {

    val query: Query0[QueryParser] = sql"""SELECT *
        FROM country c JOIN airport a ON c.code = a.iso_country JOIN runway r ON a.id = r.airport_ref
        WHERE c.code like $countryCode|| '%'
    """.query[QueryParser]

    RunQueryAndFormat(query)
  }

  def getCountriesWithTopFlopTenNbrOfRunway()(implicit db: Transactor.Aux[IO, Unit]): List[AirportsByCountry] = {
    val query: Query0[AirportsByCountry] = sql"""SELECT c.name, count(*)
        FROM country c JOIN airport a ON c.code = a.iso_country
        GROUP BY c.name
    """.query[AirportsByCountry]

    val rows = executeQuery(query)
    rows.sortWith(_.nbrOfAirports > _.nbrOfAirports).take(10) ++ rows.sortWith(_.nbrOfAirports < _.nbrOfAirports).take(10)
  }

  def getRunwaysSurfacesByCountry()(implicit db: Transactor.Aux[IO, Unit]): List[SurfaceByCountry] = {
    val query: Query0[SurfaceByCountryResponse] = sql"""SELECT c.name, r.surface
        FROM country c JOIN airport a ON c.code = a.iso_country JOIN runway r ON a.id = r.airport_ref
        GROUP BY c.name, r.surface
    """.query[SurfaceByCountryResponse]

    executeQuery(query)
      .groupBy(_.country)
      .mapValues(_.map(_.surface.getOrElse("-")))
      .map {
        case (k, v) => SurfaceByCountry(k, v)
      }.toList
  }
}
