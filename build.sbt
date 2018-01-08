name := "Lunatech"
 
version := "1.0" 
      
lazy val `lunatech` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.4"

scalacOptions ++= Seq("-Ypartial-unification")

lazy val doobieVersion = "0.5.0-M12"

libraryDependencies ++= Seq(
  jdbc ,
  ehcache ,
  ws ,
  specs2 % Test ,
  guice,
  "org.tpolecat" %% "doobie-core"     % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2"   % doobieVersion,
  "org.typelevel" %% "cats-core" % "1.0.1",
  "com.bowlingx" %% "play-webpack" % "0.1.13"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )


TwirlKeys.templateImports += "com.bowlingx.webpack.WebpackManifest"

      