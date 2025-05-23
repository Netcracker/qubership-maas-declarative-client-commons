package org.qubership.maas.declarative.kafka.client.impl.client.creator;

import org.qubership.cloud.bluegreen.api.service.BlueGreenStatePublisher;
import org.qubership.cloud.maas.bluegreen.kafka.BGKafkaConsumer;
import org.qubership.maas.declarative.kafka.client.impl.common.bg.KafkaConsumerConfiguration;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;
import java.util.function.Function;

// Used to create platform dependent clients (SpringBoot, Quarkus) with necessary settings
public interface KafkaClientCreationService {

    Producer createKafkaProducer(Map<String, Object> configs, Serializer keySerializer, Serializer valueSerializer);

    BGKafkaConsumer<?, ?> createKafkaConsumer(KafkaConsumerConfiguration kafkaConsumerConfiguration,
                                              Deserializer keyDeserializer,
                                              Deserializer valueDeserializer,
                                              String topic,
                                              Function<Map<String, Object>, Consumer> consumerSupplier,
                                              BlueGreenStatePublisher statePublisher);
}
