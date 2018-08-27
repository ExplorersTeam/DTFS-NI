package org.exp.dtfs.ni.context;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.exp.dtfs.ni.conf.KafkaConfigs;

public final class KafkaContext {
    private static Properties producerProps = new Properties();
    private static Properties consumerProps = new Properties();

    static {
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfigs.getKafkaBootstrapServers());// 必须指定
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaConfigs.getKafkaKeySerializer());// 必须指定
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaConfigs.getKafkaValueSerializer());// 必须指定
        producerProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, KafkaConfigs.getKafkaCompressionType());// 启用gzip压缩
        producerProps.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, KafkaConfigs.getKafkaMaxRequestSize());// 最大请求尺寸,默认是1M=1048576
        producerProps.put(ProducerConfig.RETRIES_CONFIG, KafkaConfigs.getKafkaRetries());// 重试次数
        producerProps.put(ProducerConfig.BATCH_SIZE_CONFIG, KafkaConfigs.getKafkaBatchSize());
        producerProps.put(ProducerConfig.LINGER_MS_CONFIG, KafkaConfigs.getKafkaLingerMs());// 传输时延

        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfigs.getKafkaBootstrapServers());// 必须指定
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaConfigs.getKafkaKeyDeserializer());// 必须指定
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaConfigs.getKafkaValueDeserializer());// 必须指定
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConfigs.getConsumerGroupID());
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, KafkaConfigs.getConsumerEnableAutoCommit());
    }

    private KafkaContext() {
        // Do nothing
    }

    public static Properties getProducerProps() {
        return ((Properties) (producerProps.clone()));
    }

    public static Properties getConsumerProps() {
        return ((Properties) (consumerProps.clone()));
    }

}
