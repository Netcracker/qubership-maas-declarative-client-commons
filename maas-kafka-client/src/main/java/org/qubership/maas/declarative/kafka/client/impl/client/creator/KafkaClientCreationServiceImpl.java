package org.qubership.maas.declarative.kafka.client.impl.client.creator;

import org.qubership.cloud.bluegreen.api.service.BlueGreenStatePublisher;
import org.qubership.cloud.maas.bluegreen.kafka.BGKafkaConsumer;
import org.qubership.cloud.maas.bluegreen.kafka.impl.BGKafkaConsumerConfig;
import org.qubership.cloud.maas.bluegreen.kafka.impl.BGKafkaConsumerImpl;
import org.qubership.cloud.maas.bluegreen.kafka.impl.DefaultKafkaConsumer;
import org.qubership.maas.declarative.kafka.client.impl.common.bg.KafkaConsumerConfiguration;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;
import java.util.function.Function;

public class KafkaClientCreationServiceImpl implements KafkaClientCreationService {

    @Override
    public Producer createKafkaProducer(Map<String, Object> configs, Serializer keySerializer, Serializer valueSerializer) {
        return new KafkaProducer(configs, keySerializer, valueSerializer);
    }

    @Override
    public BGKafkaConsumer<?, ?> createKafkaConsumer(KafkaConsumerConfiguration kafkaConsumerConfiguration,
                                                     Deserializer keyDeserializer,
                                                     Deserializer valueDeserializer,
                                                     String topic,
                                                     Function<Map<String, Object>, Consumer> consumerSupplier,
                                                     BlueGreenStatePublisher statePublisher) {
        BGKafkaConsumerConfig.Builder kafkaConsumerConfigBuilder = BGKafkaConsumerConfig.builder(
                        kafkaConsumerConfiguration.getConfigs(), topic, statePublisher)
                .consistencyMode(kafkaConsumerConfiguration.getConsumerConsistencyMode())
                .candidateOffsetSetupStrategy(kafkaConsumerConfiguration.getCandidateOffsetShift())
                .ignoreFilter(!kafkaConsumerConfiguration.isFilterEnabled())
                .deserializers(keyDeserializer, valueDeserializer);

        if (consumerSupplier != null) {
            kafkaConsumerConfigBuilder.consumerSupplier(consumerSupplier);
        }

        if (kafkaConsumerConfiguration.isBlueGreen() && !kafkaConsumerConfiguration.isVersioned()) {
            return new BGKafkaConsumerImpl<>(kafkaConsumerConfigBuilder.build());
        }

        return new DefaultKafkaConsumer<>(kafkaConsumerConfigBuilder.build());
    }
}
