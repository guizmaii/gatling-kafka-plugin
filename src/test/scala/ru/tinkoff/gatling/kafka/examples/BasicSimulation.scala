package ru.tinkoff.gatling.kafka.examples

import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import org.apache.kafka.clients.producer.ProducerConfig
import ru.tinkoff.gatling.kafka.protocol.KafkaProtocol
import ru.tinkoff.gatling.kafka.request.builder.Sender

class BasicSimulation extends Simulation {

  import ru.tinkoff.gatling.kafka.KafkaDsl._
  import io.gatling.core.Predef._

  val sender = new Sender

  val kafkaConf: KafkaProtocol =
    kafka
      .topic("test.topic")
      .properties(Map(ProducerConfig.ACKS_CONFIG -> "1"))
      .build

  val scn: ScenarioBuilder =
    scenario("Basic").exec {
      sender.send("BasicRequest", "foo")
    }
}
