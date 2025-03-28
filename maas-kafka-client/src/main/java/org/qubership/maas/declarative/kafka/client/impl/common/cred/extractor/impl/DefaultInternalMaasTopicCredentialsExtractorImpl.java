package org.qubership.maas.declarative.kafka.client.impl.common.cred.extractor.impl;

import org.qubership.cloud.maas.client.api.kafka.TopicAddress;
import org.qubership.maas.declarative.kafka.client.impl.common.cred.extractor.api.InternalMaasTopicCredentialsExtractor;

import java.util.Map;

public class DefaultInternalMaasTopicCredentialsExtractorImpl implements InternalMaasTopicCredentialsExtractor {
    @Override
    public Map<String, Object> extract(TopicAddress topic) {
        // TODO add logging
        return topic.formatConnectionProperties().orElse(Map.of());// TODO throw exception?
    }
}
