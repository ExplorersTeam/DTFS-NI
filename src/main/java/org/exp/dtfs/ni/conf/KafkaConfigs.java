package org.exp.dtfs.ni.conf;

import org.exp.dtfs.ni.common.Constants;

/**
 *
 * Kafka configurations.
 *
 * @author ZhangQingliang
 *
 */
public final class KafkaConfigs {
    /*
     * Kafka configurations.
     */
    private static final String KAFKA_BOOTSTRAP_SERVERS_CONFIG_KEY = "dtfs.ni.kafka.props.bootstrap.servers";

    private static final String KAFKA_KEY_SERIALIZER_CLASS_CONFIG_KEY = "dtfs.ni.kafka.props.key.serializer";
    private static final String DEFAULT_KEY_SERIALIZER_VALUE = "org.apache.kafka.common.serialization.StringSerializer";

    private static final String KAFKA_VALUE_SERIALIZER_CLASS_CONFIG_KEY = "dtfs.ni.kafka.props.value.serializer";
    private static final String DEFAULT_VAL_SERIALIZER_VALUE = "org.apache.kafka.common.serialization.StringSerializer";

    private static final String KAFKA_KEY_DESERIALIZER_CLASS_CONFIG_KEY = "dtfs.ni.kafka.props.key.deserializer";
    private static final String DEFAULT_KEY_DESERIALIZER_VALUE = "org.apache.kafka.common.serialization.StringDeserializer";

    private static final String KAFKA_VALUE_DESERIALIZER_CLASS_CONFIG_KEY = "dtfs.ni.kafka.props.value.deserializer";
    private static final String DEFAULT_VAL_DESERIALIZER_VALUE = "org.apache.kafka.common.serialization.StringDeserializer";

    private static final String KAFKA_COMPRESSION_TYPE_CONFIG_KEY = "dtfs.ni.kafka.props.compression.type";
    private static final String DEFAULT_KAFKA_COMPRESSION_TYPE_CONFIG_VALUE = "gzip";

    private static final String KAFKA_MAX_REQUEST_SIZE_CONFIG_KEY = "dtfs.ni.kafka.props.max.request.size";
    private static final int DEFAULT_KAFKA_MAX_REQUEST_SIZE_CONFIG_VALUE = 5_242_880;

    private static final String KAFKA_RETRIES_CONFIG_KEY = "dtfs.ni.kafka.props.retries";
    private static final int DEFAULT_KAFKA_RETRIES_CONFIG_VALUE = 3;

    private static final String KAFKA_BATCH_SIZE_CONFIG_KEY = "dtfs.ni.kafka.props.batch.size";
    private static final int DEFAULT_KAFKA_BATCH_SIZE_CONFIG_VALUE = 5_000_000;

    private static final String KAFKA_LINGER_MS_CONFIG_KEY = "dtfs.ni.kafka.props.linger.ms";
    private static final int DEFAULT_KAFKA_LINGER_MS_CONFIG_VALUE = 60000;

    private static final String KAFKA_CONSUMER_ENABLE_AUTO_COMMIT_KEY = "dtfs.ni.kafka.consumer.enable.auto.commit";
    private static final boolean DEFAULT_CONSUMER_ENABLE_AUTO_COMMIT_VALUE = true;

    private static final String KAFKA_CONSUMER_GROUP_ID_KEY = "dtfs.ni.kafka.consumer.group.id";

    private static final String CONSUMER_TIMEOUT_KEY = "dtfs.ni.kafka.consumer.timeout";
    private static final long DEFAULT_CONSUMER_TIMEOUT_VALUE = 1000;

    /*
     * Below 3 configurations no used.
     */
    private static final String KAFKA_ACKS_CONF_KEY = "dtfs.ni.kafka.props.acks";
    private static final String DEFAULT_KAFKA_ACKS_CONF_VALUE = "1";

    private static final String KAFKA_BUFFER_MEMORY_CONFIG_KEY = "dtfs.ni.kafka.props.buffer.memory";
    private static final int DEFAULT_KAFKA_BUFFER_MEMORY_CONFIG_VALUE = 33_554_432;

    private static final String KAFKA_MAX_BLOCK_MS_CONFIG_KEY = "dtfs.ni.kafka.props.max.block.ms";
    private static final int DEFAULT_KAFKA_MAX_BLOCK_MS_CONFIG_VALUE = 3000;

    /*
     * Kafka topic name.
     */
    private static final String KAFKA_METRIC_TOPIC_NAME_KEY = "dtfs.ni.kafka.topic.metric.name";
    private static final String KAFKA_LOG_TOPIC_NAME_KEY = "dtfs.ni.kafka.topic.log.name";
    private static final String KAFKA_LOG_FLUME_TMP_TOPIC_NAME_KEY = "dtfs.ni.kafka.topic.flume.tmp.name";

    private KafkaConfigs() {
        // Do nothing
    }

    public static String getKafkaBootstrapServers() {
        return Configs.get(KAFKA_BOOTSTRAP_SERVERS_CONFIG_KEY, Constants.DEFAULT_ADDR_STR);
    }

    public static String getKafkaKeySerializer() {
        return Configs.get(KAFKA_KEY_SERIALIZER_CLASS_CONFIG_KEY, DEFAULT_KEY_SERIALIZER_VALUE);
    }

    public static String getKafkaValueSerializer() {
        return Configs.get(KAFKA_VALUE_SERIALIZER_CLASS_CONFIG_KEY, DEFAULT_VAL_SERIALIZER_VALUE);
    }

    public static String getKafkaKeyDeserializer() {
        return Configs.get(KAFKA_KEY_DESERIALIZER_CLASS_CONFIG_KEY, DEFAULT_KEY_DESERIALIZER_VALUE);
    }

    public static String getKafkaValueDeserializer() {
        return Configs.get(KAFKA_VALUE_DESERIALIZER_CLASS_CONFIG_KEY, DEFAULT_VAL_DESERIALIZER_VALUE);
    }

    public static String getKafkaCompressionType() {
        return Configs.get(KAFKA_COMPRESSION_TYPE_CONFIG_KEY, DEFAULT_KAFKA_COMPRESSION_TYPE_CONFIG_VALUE);
    }

    public static int getKafkaMaxRequestSize() {
        return Configs.getInt(KAFKA_MAX_REQUEST_SIZE_CONFIG_KEY, DEFAULT_KAFKA_MAX_REQUEST_SIZE_CONFIG_VALUE);
    }

    public static int getKafkaRetries() {
        return Configs.getInt(KAFKA_RETRIES_CONFIG_KEY, DEFAULT_KAFKA_RETRIES_CONFIG_VALUE);
    }

    public static int getKafkaBatchSize() {
        return Configs.getInt(KAFKA_BATCH_SIZE_CONFIG_KEY, DEFAULT_KAFKA_BATCH_SIZE_CONFIG_VALUE);
    }

    public static int getKafkaLingerMs() {
        return Configs.getInt(KAFKA_LINGER_MS_CONFIG_KEY, DEFAULT_KAFKA_LINGER_MS_CONFIG_VALUE);
    }

    public static String getKafkaAcks() {
        return Configs.get(KAFKA_ACKS_CONF_KEY, DEFAULT_KAFKA_ACKS_CONF_VALUE);
    }

    public static int getKafkaBufferMemory() {
        return Configs.getInt(KAFKA_BUFFER_MEMORY_CONFIG_KEY, DEFAULT_KAFKA_BUFFER_MEMORY_CONFIG_VALUE);
    }

    public static int getKafkaMaxBlockMs() {
        return Configs.getInt(KAFKA_MAX_BLOCK_MS_CONFIG_KEY, DEFAULT_KAFKA_MAX_BLOCK_MS_CONFIG_VALUE);
    }

    public static String getKafkaMetricTopicName() {
        return Configs.get(KAFKA_METRIC_TOPIC_NAME_KEY, Constants.DEFAULT_SERVICE_NAME);
    }

    public static String getKafkaLogTopicName() {
        return Configs.get(KAFKA_LOG_TOPIC_NAME_KEY, Constants.DEFAULT_SERVICE_NAME);
    }

    public static String getKafkaLogFlumeTmpTopicName() {
        return Configs.get(KAFKA_LOG_FLUME_TMP_TOPIC_NAME_KEY, Constants.DEFAULT_SERVICE_NAME);
    }

    public static boolean getConsumerEnableAutoCommit() {
        return Configs.getBoolean(KAFKA_CONSUMER_ENABLE_AUTO_COMMIT_KEY, DEFAULT_CONSUMER_ENABLE_AUTO_COMMIT_VALUE);
    }

    public static String getConsumerGroupID() {
        return Configs.get(KAFKA_CONSUMER_GROUP_ID_KEY, Constants.DEFAULT_SERVICE_NAME);
    }

    public static long getConsumerTimeout() {
        return Configs.getLong(CONSUMER_TIMEOUT_KEY, DEFAULT_CONSUMER_TIMEOUT_VALUE);
    }

}
