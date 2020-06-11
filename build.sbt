lazy val commonSettings = Seq(
  organization := "de.algenubi",
  version := "0.1.0",
  scalaVersion := "2.12.9"
)

lazy val plahato = Project(id = "plahato", base = file(".")).
  settings(commonSettings: _*).
  settings(
    name := "plahato"
  )

githubOwner := "jdjmuc"
githubRepository := "plahato"

// Scala XML
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.2.0"

// for ScalaTest
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.8"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"

// for Play libraries
libraryDependencies += "com.typesafe.play" %% "play" % "2.8.1"
libraryDependencies += "com.typesafe.play" %% "play-cache" % "2.8.1"

// for better I/O
libraryDependencies += "commons-io" % "commons-io" % "2.5"

// for mongo db
libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.7.0"

libraryDependencies += "commons-codec" % "commons-codec" % "1.13"

resolvers += "Atlassian Releases" at "https://maven.atlassian.com/public/"

