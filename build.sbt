name := """playlearn1"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  evolutions
)

libraryDependencies += "com.datastax.cassandra" % "cassandra-driver-core" % "2.1.6"

libraryDependencies += "com.datastax.cassandra" % "cassandra-driver-mapping" % "2.1.6"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
