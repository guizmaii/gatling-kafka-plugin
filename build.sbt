import Dependencies._

lazy val root = (project in file("."))
  .enablePlugins(GitVersioning)
  .settings(
    name := "gatling-kafka-plugin",
    libraryDependencies ++= gatling,
    libraryDependencies ++= kafka,
    libraryDependencies ++= avro4s,
  )
