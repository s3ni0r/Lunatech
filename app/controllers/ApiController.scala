package controllers

import javax.inject._
import akka.actor.ActorSystem
import api.Api
import cats.effect.IO
import doobie.util.transactor.Transactor
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import play.api.Configuration
import play.api.libs.json.Json

@Singleton
class ApiController @Inject()(api: Api, config: Configuration, cc: ControllerComponents, actorSystem: ActorSystem)(
    implicit exec: ExecutionContext)
    extends AbstractController(cc) {

  lazy implicit val db = Transactor.fromDriverManager[IO](
    config.get[String]("db.default.driver"),
    config.get[String]("db.default.url"),
    config.get[String]("db.default.username"),
    config.get[String]("db.default.password")
  )

  def airportRunwaysByCountryName(name: String) = Action.async {
    val countries = api.getAirportRunwaysByCountryName(name)
    Future(Ok(Json.toJson(countries)))
  }

  def airportRunwaysByCountryCode(code: String) = Action.async {
    val countries = api.getAirportRunwaysByCountryCode(code)
    Future(Ok(Json.toJson(countries)))
  }

  def countriesWithTopFlopTenNbrOfRunway = Action.async {
    val stats = api.getCountriesWithTopFlopTenNbrOfRunway()
    Future(Ok(Json.toJson(stats)))
  }

  def runwaysSurfacesByCountry = Action.async {
    val res = api.getRunwaysSurfacesByCountry()
    Future(Ok(Json.toJson(res)))
  }
}
