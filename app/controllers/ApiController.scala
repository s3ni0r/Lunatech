package controllers

import javax.inject._

import akka.actor.ActorSystem
import cats.effect.IO
import doobie.util.transactor.Transactor
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import model.Queries
import play.api.libs.json.Json

@Singleton
class ApiController @Inject()(cc: ControllerComponents, actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends AbstractController(cc) with Queries {
  lazy implicit val db  = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver", "jdbc:postgresql://localhost:5432/lunatech", "lunatech", "lunatech"
  )

  def airportRunwaysByCountryName(name: String) = Action.async {
    val countries = getAirportRunwaysByCountryName(name)
    Future(Ok(Json.toJson(countries)))
  }

  def airportRunwaysByCountryCode(code: String) = Action.async {
    val countries = getAirportRunwaysByCountryName(code)
    Future(Ok(Json.toJson(countries)))
  }

  def countriesWithTopFlopTenNbrOfRunway = Action.async {
    val stats = getCountriesWithTopFlopTenNbrOfRunway()
    Future(Ok(Json.toJson(stats)))
  }

  def runwaysSurfacesByCountry = Action.async{
    val res = getRunwaysSurfacesByCountry()
    Future(Ok(Json.toJson(res)))
  }
}
