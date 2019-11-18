package ru.tinkoff.gatling.kafka

import io.gatling.core.session.Expression
import ru.tinkoff.gatling.kafka.protocol.KafkaProtocolBuilder

object KafkaDsl {

  final case class KafkaAttributes[K, V](requestName: Expression[String], key: Option[Expression[K]], payload: Expression[V])


  final val kafka: KafkaProtocolBuilder.type = KafkaProtocolBuilder

}
