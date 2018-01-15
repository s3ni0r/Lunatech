logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.10")

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.8.0")

addSbtPlugin("com.iheart" % "sbt-play-swagger" % "0.6.2-PLAY2.6")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.6")