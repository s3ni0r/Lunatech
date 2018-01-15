name := "Lunatech"

version := "1.0"

lazy val `lunatech` = (project in file(".")).enablePlugins(PlayScala, SwaggerPlugin)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

scalaVersion := "2.12.4"

scalacOptions ++= Seq("-Ypartial-unification")

lazy val doobieVersion = "0.5.0-M12"

lazy val testDependencies = Seq(
  "com.h2database" % "h2"          % "1.4.196",
  "org.scalamock"  %% "scalamock"  % "4.0.0",
  "org.scalatest"  %% "scalatest"  % "3.0.4",
  "org.mockito"    % "mockito-all" % "1.10.19"
).map(_ % "test")

libraryDependencies ++= Seq(
  jdbc,
  ehcache,
  ws,
  specs2 % Test,
  guice,
  "org.tpolecat"  %% "doobie-core"     % doobieVersion,
  "org.tpolecat"  %% "doobie-postgres" % doobieVersion,
  "org.tpolecat"  %% "doobie-specs2"   % doobieVersion,
  "org.typelevel" %% "cats-core"       % "1.0.1",
  "org.webjars" % "swagger-ui" % "2.2.0"
) ++ testDependencies

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

swaggerDomainNameSpaces := Seq("models")

mainClass in assembly := Some("play.core.server.ProdServerStart")
fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)

assemblyMergeStrategy in assembly := {
  case manifest if manifest.contains("MANIFEST.MF") =>
    // We don't need manifest files since sbt-assembly will create
    // one with the given settings
    MergeStrategy.discard
  case referenceOverrides if referenceOverrides.contains("reference-overrides.conf") =>
    // Keep the content for all reference-overrides.conf files
    MergeStrategy.concat
  case x =>
    // For all the other files, use the default sbt-assembly merge strategy
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
