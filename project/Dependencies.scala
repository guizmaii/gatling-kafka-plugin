import sbt._

object Dependencies {

  lazy val gatling: Seq[ModuleID] = ((version: String) =>
    Seq(
      "io.gatling"            % "gatling-core"              % version % Provided,
      "io.gatling.highcharts" % "gatling-charts-highcharts" % version % Test,
      "io.gatling"            % "gatling-test-framework"    % version % Test
    ))("3.3.1")

  lazy val kafka: Seq[ModuleID] = Seq(
    ("org.apache.kafka" % "kafka-clients" % "2.3.1")
      .exclude("org.slf4j", "slf4j-api"))

  lazy val avro4s: Seq[ModuleID] = Seq("com.sksamuel.avro4s" %% "avro4s-core" % "3.0.4" % "provided")

}
