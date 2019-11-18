package ru.tinkoff.gatling.kafka.request.builder

import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.session.Expression
import io.gatling.core.structure.ScenarioContext
import org.apache.kafka.clients.producer.KafkaProducer
import ru.tinkoff.gatling.kafka.KafkaDsl.KafkaAttributes
import ru.tinkoff.gatling.kafka.actions.KafkaRequestAction
import ru.tinkoff.gatling.kafka.protocol.KafkaProtocol
import ru.tinkoff.gatling.kafka.protocol.KafkaProtocol.Components

final class Sender {

  import scala.collection.JavaConverters._

  private def doSend[K, V](requestName: Expression[String], key: Option[Expression[K]], payload: Expression[V]): ActionBuilder =
    (ctx: ScenarioContext, next: Action) => {
      import ctx._

      val kafkaComponents: Components = protocolComponentsRegistry.components(KafkaProtocol.kafkaProtocolKey)

      val producer = new KafkaProducer[K, V](kafkaComponents.kafkaProtocol.properties.asJava)

      coreComponents.actorSystem.registerOnTermination(producer.close())

      new KafkaRequestAction(
        producer,
        KafkaAttributes(requestName, key, payload),
        coreComponents,
        kafkaComponents.kafkaProtocol,
        throttled,
        next
      )
    }

  def send[V](requestName: Expression[String], payload: Expression[V]): ActionBuilder =
    doSend(requestName, None, payload)

  def send[K, V](requestName: Expression[String], key: Expression[K], payload: Expression[V]): ActionBuilder =
    doSend(requestName, Some(key), payload)


}
