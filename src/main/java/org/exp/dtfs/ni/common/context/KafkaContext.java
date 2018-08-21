package org.exp.dtfs.ni.common.context;

import java.util.Properties;

import org.exp.dtfs.ni.common.conf.KafkaConfigs;

public class KafkaContext {
    private static Properties producerProps = new Properties();

    static {
        producerProps.put("bootstrap.servers", KafkaConfigs.getKafkaBootstrapServers());// 必须指定
        producerProps.put("key.serializer", KafkaConfigs.getKafkaKeySerializer());// 必须指定
        producerProps.put("value.serializer", KafkaConfigs.getKafkaValueSerializer());// 必须指定
        producerProps.put("compression.type", KafkaConfigs.getKafkaCompressionType());// 启用gzip压缩
        producerProps.put("max.request.size", KafkaConfigs.getKafkaMaxRequestSize());// 最大请求尺寸,默认是1M=1048576
        producerProps.put("retries", KafkaConfigs.getKafkaRetries());// 重试次数
        producerProps.put("batch.size", KafkaConfigs.getKafkaBatchSize());
        producerProps.put("linger.ms", KafkaConfigs.getKafkaLingerMs());// 传输时延
    }

    private KafkaContext() {
        // Do nothing
    }

    public static Properties getProducerProps() {
        return producerProps;
    }

}
