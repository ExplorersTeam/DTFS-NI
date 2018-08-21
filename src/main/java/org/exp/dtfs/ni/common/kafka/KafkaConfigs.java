package org.exp.dtfs.ni.common.kafka;

import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.utils.PropertiesAdmin;

public class KafkaConfigs {
    private static final PropertiesAdmin PROPS = new PropertiesAdmin(Constants.KAFKA_PROPS_FILENAME);

    /*
     * Kafka configurations.
     */
    private static final String KAFKA_BOOTSTRAP_SERVERS_CONFIG_KEY = "dtfs.ni.kafka.props.bootstrap.servers";
    private static final String DEFAULT_KAFKA_BOOTSTRAP_SERVERS_CONFIG_VALUE = "134.108.3.27:9092,134.108.3.28:9092,134.108.3.29:9092";

    private static final String KAFKA_KEY_SERIALIZER_CLASS_CONFIG_KEY = "dtfs.ni.kafka.props.key.serializer";
    private static final String DEFAULT_KAFKA_KEY_SERIALIZER_CLASS_CONFIG_VALUE = "org.apache.kafka.common.serialization.StringSerializer";

    private static final String KAFKA_VALUE_SERIALIZER_CLASS_CONFIG_KEY = "dtfs.ni.kafka.props.value.serializer";
    private static final String DEFAULT_KAFKA_VALUE_SERIALIZER_CLASS_CONFIG_VALUE = "org.apache.kafka.common.serialization.StringSerializer";

    private static final String KAFKA_COMPRESSION_TYPE_CONFIG_KEY = "dtfs.ni.kafka.props.compression.type";
    private static final String DEFAULT_KAFKA_COMPRESSION_TYPE_CONFIG_VALUE = "gzip";

    private static final String KAFKA_MAX_REQUEST_SIZE_CONFIG_KEY = "dtfs.ni.kafka.props.max.request.size";
    private static final int DEFAULT_KAFKA_MAX_REQUEST_SIZE_CONFIG_VALUE = 5242880;

    private static final String KAFKA_RETRIES_CONFIG_KEY = "dtfs.ni.kafka.props.retries";
    private static final int DEFAULT_KAFKA_RETRIES_CONFIG_VALUE = 3;

    private static final String KAFKA_BATCH_SIZE_CONFIG_KEY = "dtfs.ni.kafka.props.batch.size";
    private static final int DEFAULT_KAFKA_BATCH_SIZE_CONFIG_VALUE = 5000000;

    private static final String KAFKA_LINGER_MS_CONFIG_KEY = "dtfs.ni.kafka.props.linger.ms";
    private static final int DEFAULT_KAFKA_LINGER_MS_CONFIG_VALUE = 60000;

    /*
     * Below 3 configurations no used.
     */
    private static final String KAFKA_ACKS_CONF_KEY = "dtfs.ni.kafka.props.acks";
    private static final String DEFAULT_KAFKA_ACKS_CONF_VALUE = "1";

    private static final String KAFKA_BUFFER_MEMORY_CONFIG_KEY = "dtfs.ni.kafka.props.buffer.memory";
    private static final int DEFAULT_KAFKA_BUFFER_MEMORY_CONFIG_VALUE = 33554432;

    private static final String KAFKA_MAX_BLOCK_MS_CONFIG_KEY = "dtfs.ni.kafka.props.max.block.ms";
    private static final int DEFAULT_KAFKA_MAX_BLOCK_MS_CONFIG_VALUE = 3000;

    /*
     * Kafka topic name.
     */
    private static final String KAFKA_TOPIC_NAME_KEY = "dtfs.ni.kafka.topic.name";
    private static final String DEFAULT_KAFKA_TOPIC_NAME_VALUE = "ETE_CTDFS_01";

    private KafkaConfigs() {
        // Do nothing
    }

    private static PropertiesAdmin getProps() {
        return PROPS;
    }

    public static String getKafkaBootstrapServers() {
        return getProps().get(KAFKA_BOOTSTRAP_SERVERS_CONFIG_KEY, DEFAULT_KAFKA_BOOTSTRAP_SERVERS_CONFIG_VALUE);
    }

    public static String getKafkaKeySerializer() {
        return getProps().get(KAFKA_KEY_SERIALIZER_CLASS_CONFIG_KEY, DEFAULT_KAFKA_KEY_SERIALIZER_CLASS_CONFIG_VALUE);
    }

    public static String getKafkaValueSerializer() {
        return getProps().get(KAFKA_VALUE_SERIALIZER_CLASS_CONFIG_KEY, DEFAULT_KAFKA_VALUE_SERIALIZER_CLASS_CONFIG_VALUE);
    }

    public static String getKafkaCompressionType() {
        return getProps().get(KAFKA_COMPRESSION_TYPE_CONFIG_KEY, DEFAULT_KAFKA_COMPRESSION_TYPE_CONFIG_VALUE);
    }

    public static int getKafkaMaxRequestSize() {
        return getProps().getInt(KAFKA_MAX_REQUEST_SIZE_CONFIG_KEY, DEFAULT_KAFKA_MAX_REQUEST_SIZE_CONFIG_VALUE);
    }

    public static int getKafkaRetries() {
        return getProps().getInt(KAFKA_RETRIES_CONFIG_KEY, DEFAULT_KAFKA_RETRIES_CONFIG_VALUE);
    }

    public static int getKafkaBatchSize() {
        return getProps().getInt(KAFKA_BATCH_SIZE_CONFIG_KEY, DEFAULT_KAFKA_BATCH_SIZE_CONFIG_VALUE);
    }

    public static int getKafkaLingerMs() {
        return getProps().getInt(KAFKA_LINGER_MS_CONFIG_KEY, DEFAULT_KAFKA_LINGER_MS_CONFIG_VALUE);
    }

    public static String getKafkaAcks() {
        return getProps().get(KAFKA_ACKS_CONF_KEY, DEFAULT_KAFKA_ACKS_CONF_VALUE);
    }

    public static int getKafkaBufferMemory() {
        return getProps().getInt(KAFKA_BUFFER_MEMORY_CONFIG_KEY, DEFAULT_KAFKA_BUFFER_MEMORY_CONFIG_VALUE);
    }

    public static int getKafkaMaxBlockMs() {
        return getProps().getInt(KAFKA_MAX_BLOCK_MS_CONFIG_KEY, DEFAULT_KAFKA_MAX_BLOCK_MS_CONFIG_VALUE);
    }

    public static String getKafkaTopicName() {
        return getProps().get(KAFKA_TOPIC_NAME_KEY, DEFAULT_KAFKA_TOPIC_NAME_VALUE);
    }

}
