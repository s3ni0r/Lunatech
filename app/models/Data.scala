package models
import play.api.libs.json.Json

object Data {

  case class Airport(id: Long,
                     ident: String,
                     `type`: String,
                     name: String,
                     latitude_deg: Float,
                     longitude_deg: Float,
                     elevation_ft: Option[Long],
                     continent: String,
                     iso_country: String,
                     iso_region: String,
                     municipality: Option[String],
                     scheduled_service: String,
                     gps_code: Option[String],
                     iata_code: Option[String],
                     local_code: Option[String],
                     home_link: Option[String],
                     wikipedia_link: Option[String],
                     keywords: Option[String])

  object Airport {
    implicit val format = Json.format[Airport]
  }

  case class Country(id: Long,
                     code: String,
                     name: String,
                     continent: String,
                     wikipedia_link: String,
                     keywords: Option[String])

  object Country {
    implicit val format = Json.format[Country]
  }

  case class Runway(id: Long,
                    airport_ref: Long,
                    airport_ident: String,
                    length_ft: Option[Long],
                    width_ft: Option[Long],
                    surface: Option[String],
                    lighted: Int,
                    closed: Int,
                    le_ident: Option[String],
                    le_latitude_deg: Option[Float],
                    le_longitude_deg: Option[Float],
                    le_elevation_ft: Option[Long],
                    le_heading_degT: Option[Long],
                    le_displaced_threshold_ft: Option[Long],
                    he_ident: Option[String],
                    he_latitude_deg: Option[Float],
                    he_longitude_deg: Option[Float],
                    he_elevation_ft: Option[Long],
                    he_heading_degT: Option[Float],
                    he_displaced_threshold_ft: Option[Long])

  object Runway {
    implicit val format = Json.format[Runway]
  }

  case class QueryParser(country: Country, airport: Airport, runway: Runway)

  case class AirportRunways(airportName: String, runways: List[Runway])

  object AirportRunways {
    implicit val format = Json.format[AirportRunways]
  }

  case class QueryResult(countryName: String, countryCode: String, airports: List[AirportRunways])

  object QueryResult {
    implicit val format = Json.format[QueryResult]
  }

  case class AirportsByCountry(name: String, nbrOfAirports: Int)

  object AirportsByCountry {
    implicit val format = Json.format[AirportsByCountry]
  }

  case class SurfaceByCountryResponse(country: String, surface: Option[String])

  case class SurfaceByCountry(country: String, surfaces: List[String])

  object SurfaceByCountry {
    implicit val format = Json.format[SurfaceByCountry]
  }

}
