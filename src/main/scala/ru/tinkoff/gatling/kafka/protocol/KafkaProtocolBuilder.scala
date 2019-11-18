package ru.tinkoff.gatling.kafka.protocol

case object KafkaProtocolBuilder {

  final def topic(name: String) = KafkaProtocolBuilderPropertiesStep(name, Map.empty[String, Object])

}

final case class KafkaProtocolBuilder(topic: String, props: Map[String, Object]) {
  def build = new KafkaProtocol(topic, props)
}
