package org.exp.dtfs.ni.context;

import java.util.Properties;

import org.exp.dtfs.ni.conf.KafkaConfigs;

public final class KafkaContext {
    private static Properties props = new Properties();

    static {
        props.put("bootstrap.servers", KafkaConfigs.getKafkaBootstrapServers());// 必须指定
        props.put("key.serializer", KafkaConfigs.getKafkaKeySerializer());// 必须指定
        props.put("value.serializer", KafkaConfigs.getKafkaValueSerializer());// 必须指定
        props.put("compression.type", KafkaConfigs.getKafkaCompressionType());// 启用gzip压缩
    }

    private KafkaContext() {
        // Do nothing
    }

    public static Properties getProducerProps() {
        Properties producerProps = new Properties(props);
        producerProps.put("max.request.size", KafkaConfigs.getKafkaMaxRequestSize());// 最大请求尺寸,默认是1M=1048576
        producerProps.put("retries", KafkaConfigs.getKafkaRetries());// 重试次数
        producerProps.put("batch.size", KafkaConfigs.getKafkaBatchSize());
        producerProps.put("linger.ms", KafkaConfigs.getKafkaLingerMs());// 传输时延
        return producerProps;
    }

    public static Properties getConsumerProps() {
        Properties consumerProps = new Properties(props);
        consumerProps.put("enable.auto.commit", KafkaConfigs.getConsumerEnableAutoCommit());
        return consumerProps;
    }

}
