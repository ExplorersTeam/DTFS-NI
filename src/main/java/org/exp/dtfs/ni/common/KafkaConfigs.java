package org.exp.dtfs.ni.common;

import org.apache.hadoop.conf.Configuration;

public class KafkaConfigs {
    // Configuration file name.
    private static final String CONF_FILENAME = "dtfs-ni-kafka-site.xml";

    /*
     * Kafka configurations.
     */
    private static final String KAFKA_BOOTSTRAP_SERVERS_CONFIG_KEY = "kafka.props.bootstrap.servers";
    private static final String DEFAULT_KAFKA_BOOTSTRAP_SERVERS_CONFIG_VALUE = "134.108.3.27:9092,134.108.3.28:9092,134.108.3.29:9092";

    private static final String KAFKA_KEY_SERIALIZER_CLASS_CONFIG_KEY = "kafka.props.key.serializer";
    private static final String DEFAULT_KAFKA_KEY_SERIALIZER_CLASS_CONFIG_VALUE = "org.apache.kafka.common.serialization.StringSerializer";

    private static final String KAFKA_VALUE_SERIALIZER_CLASS_CONFIG_KEY = "kafka.props.value.serializer";
    private static final String DEFAULT_KAFKA_VALUE_SERIALIZER_CLASS_CONFIG_VALUE = "org.apache.kafka.common.serialization.StringSerializer";

    private static final String KAFKA_COMPRESSION_TYPE_CONFIG_KEY = "kafka.props.compression.type";
    private static final String DEFAULT_KAFKA_COMPRESSION_TYPE_CONFIG_VALUE = "gzip";

    private static final String KAFKA_MAX_REQUEST_SIZE_CONFIG_KEY = "kafka.props.max.request.size";
    private static final int DEFAULT_KAFKA_MAX_REQUEST_SIZE_CONFIG_VALUE = 5242880;

    private static final String KAFKA_RETRIES_CONFIG_KEY = "kafka.props.retries";
    private static final int DEFAULT_KAFKA_RETRIES_CONFIG_VALUE = 3;

    private static final String KAFKA_BATCH_SIZE_CONFIG_KEY = "kafka.props.batch.size";
    private static final int DEFAULT_KAFKA_BATCH_SIZE_CONFIG_VALUE = 5000000;

    private static final String KAFKA_LINGER_MS_CONFIG_KEY = "kafka.props.linger.ms";
    private static final int DEFAULT_KAFKA_LINGER_MS_CONFIG_VALUE = 60000;

    /*
     * Below 3 configurations no used.
     */
    private static final String KAFKA_ACKS_CONF_KEY = "kafka.props.acks";
    private static final String DEFAULT_KAFKA_ACKS_CONF_VALUE = "1";

    private static final String KAFKA_BUFFER_MEMORY_CONFIG_KEY = "kafka.props.buffer.memory";
    private static final int DEFAULT_KAFKA_BUFFER_MEMORY_CONFIG_VALUE = 33554432;

    private static final String KAFKA_MAX_BLOCK_MS_CONFIG_KEY = "kafka.props.max.block.ms";
    private static final int DEFAULT_KAFKA_MAX_BLOCK_MS_CONFIG_VALUE = 3000;

    /*
     * Kafka topic name.
     */
    private static final String KAFKA_TOPIC_NAME_KEY = "kafka.topic.name";
    private static final String DEFAULT_KAFKA_TOPIC_NAME_VALUE = "ETE_CTDFS_01";

    private static final Configuration CONF = new Configuration();

    static {
        CONF.addResource(CONF_FILENAME);
    }

    private KafkaConfigs() {
        // Do nothing
    }

    public static Configuration getConf() {
        return CONF;
    }

    public static String getKafkaBootstrapServers() {
        return getConf().getTrimmed(KAFKA_BOOTSTRAP_SERVERS_CONFIG_KEY, DEFAULT_KAFKA_BOOTSTRAP_SERVERS_CONFIG_VALUE);
    }

    public static String getKafkaKeySerializer() {
        return getConf().getTrimmed(KAFKA_KEY_SERIALIZER_CLASS_CONFIG_KEY, DEFAULT_KAFKA_KEY_SERIALIZER_CLASS_CONFIG_VALUE);
    }

    public static String getKafkaValueSerializer() {
        return getConf().getTrimmed(KAFKA_VALUE_SERIALIZER_CLASS_CONFIG_KEY, DEFAULT_KAFKA_VALUE_SERIALIZER_CLASS_CONFIG_VALUE);
    }

    public static String getKafkaCompressionType() {
        return getConf().getTrimmed(KAFKA_COMPRESSION_TYPE_CONFIG_KEY, DEFAULT_KAFKA_COMPRESSION_TYPE_CONFIG_VALUE);
    }

    public static int getKafkaMaxRequestSize() {
        return getConf().getInt(KAFKA_MAX_REQUEST_SIZE_CONFIG_KEY, DEFAULT_KAFKA_MAX_REQUEST_SIZE_CONFIG_VALUE);
    }

    public static int getKafkaRetries() {
        return getConf().getInt(KAFKA_RETRIES_CONFIG_KEY, DEFAULT_KAFKA_RETRIES_CONFIG_VALUE);
    }

    public static int getKafkaBatchSize() {
        return getConf().getInt(KAFKA_BATCH_SIZE_CONFIG_KEY, DEFAULT_KAFKA_BATCH_SIZE_CONFIG_VALUE);
    }

    public static int getKafkaLingerMs() {
        return getConf().getInt(KAFKA_LINGER_MS_CONFIG_KEY, DEFAULT_KAFKA_LINGER_MS_CONFIG_VALUE);
    }

    public static String getKafkaAcks() {
        return getConf().getTrimmed(KAFKA_ACKS_CONF_KEY, DEFAULT_KAFKA_ACKS_CONF_VALUE);
    }

    public static int getKafkaBufferMemory() {
        return getConf().getInt(KAFKA_BUFFER_MEMORY_CONFIG_KEY, DEFAULT_KAFKA_BUFFER_MEMORY_CONFIG_VALUE);
    }

    public static int getKafkaMaxBlockMs() {
        return getConf().getInt(KAFKA_MAX_BLOCK_MS_CONFIG_KEY, DEFAULT_KAFKA_MAX_BLOCK_MS_CONFIG_VALUE);
    }

    public static String getKafkaTopicName() {
        return getConf().getTrimmed(KAFKA_TOPIC_NAME_KEY, DEFAULT_KAFKA_TOPIC_NAME_VALUE);
    }
}
