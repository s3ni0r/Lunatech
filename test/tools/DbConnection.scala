package tools

import java.sql.{Connection, DriverManager}

import cats.effect.IO
import com.typesafe.config.ConfigFactory
import doobie.util.transactor.Transactor
import doobie.util.transactor.Transactor.Aux
import org.scalatest.{FreeSpec, Matchers}

import scala.io.Source

abstract class DbConnection extends FreeSpec with Matchers {

  protected def createSchema(con: Connection): Unit = {
    val st = con.createStatement()
    val sql = List("schema.sql", "country.sql", "airport.sql", "runway.sql").map { table =>
      Source
        .fromInputStream(Thread.currentThread().getContextClassLoader.getResourceAsStream(table))
        .getLines()
        .mkString("\n")
    }.map(st.execute)
    st.close()
  }

  protected def withInMemoryDb(block: Aux[IO, Unit] => Unit) = {
    val config = ConfigFactory.load()
    val connection = DriverManager.getConnection(config.getString("db.default.url"),
      config.getString("db.default.username"),
      config.getString("db.default.password"))
    val db = Transactor.fromDriverManager[IO](config.getString("db.default.driver"),
      config.getString("db.default.url"),
      config.getString("db.default.username"),
      config.getString("db.default.password")
    )
    try {
      createSchema(connection)
      block.apply(db)
    } finally {
      connection.close()
    }
  }
}
