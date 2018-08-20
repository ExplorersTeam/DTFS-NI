package org.exp.dtfs.ni.common;

import java.util.Properties;

public class KafkaContext {

    private static Properties producerProps = new Properties();
    static {
        producerProps.put("bootstrap.servers", KafkaConfigs.getKafkaBootstrapServers());// 必须指定
        producerProps.put("key.serializer", KafkaConfigs.getKafkaKeySerializer());// 必须指定
        producerProps.put("value.serializer", KafkaConfigs.getKafkaValueSerializer());// 必须指定
        producerProps.put("retries", KafkaConfigs.getKafkaRetries());

        producerProps.put("acks", KafkaConfigs.getKafkaAcks());
        producerProps.put("batch.size", KafkaConfigs.getKafkaBatchSize());
        producerProps.put("linger.ms", KafkaConfigs.getKafkaLingerMs());
        producerProps.put("buffer.memory", KafkaConfigs.getKafkaBufferMemory());
        producerProps.put("max.block.ms", KafkaConfigs.getKafkaMaxBlockMs());
    }

    public static Properties getProps() {
        return producerProps;
    }

    private KafkaContext() {
        // Do nothing
    }
}
