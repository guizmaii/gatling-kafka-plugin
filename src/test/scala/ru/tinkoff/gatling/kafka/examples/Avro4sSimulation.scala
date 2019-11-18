package ru.tinkoff.gatling.kafka.examples

import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import org.apache.kafka.clients.producer.ProducerConfig
import ru.tinkoff.gatling.kafka.protocol.KafkaProtocol
import ru.tinkoff.gatling.kafka.request.builder.Sender

import scala.concurrent.duration._
import scala.language.postfixOps

class Avro4sSimulation extends Simulation {

  import io.gatling.core.Predef._
  import ru.tinkoff.gatling.kafka.KafkaDsl._

  val kafkaConf: KafkaProtocol = kafka
  // Kafka topic name
    .topic("test")
    // Kafka producer configs
    .properties(
      Map(
        ProducerConfig.ACKS_CONFIG -> "1",
        // list of Kafka broker hostname and port pairs
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG -> "localhost:9092",
        // in most cases, StringSerializer or ByteArraySerializer
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG ->
          "org.apache.kafka.common.serialization.StringSerializer",
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG ->
          "org.apache.kafka.common.serialization.StringSerializer"
      ))
    .build

  case class Ingredient(name: String, sugar: Double, fat: Double)


  val sender = new Sender

  val scn: ScenarioBuilder =
    scenario("Kafka Test")
    .exec {
      sender.send("Simple Request", Ingredient("Cheese", 0d, 70d))
    }
    .exec {
      sender.send("Simple Request with Key", "Key", Ingredient("Cheese", 0d, 70d))
    }


  setUp(scn.inject(constantUsersPerSec(10) during (90 seconds))).protocols(kafkaConf)
}
