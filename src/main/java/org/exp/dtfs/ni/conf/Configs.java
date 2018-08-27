package org.exp.dtfs.ni.conf;

import org.apache.hadoop.conf.Configuration;

public final class Configs {
    private static final Configuration CONF = new Configuration();

    /*
     * Configuration file name.
     */
    private static final String COMMON_CONF_FILENAME = "dtfs-ni-common-site.xml";
    private static final String AMBARI_CONF_FILENAME = "dtfs-ni-ambari-site.xml";
    private static final String KAFKA_CONF_FILENAME = "dtfs-ni-kafka-site.xml";
    private static final String REST_CONF_FILENAME = "dtfs-ni-rest-site.xml";
    private static final String HADOOP_CONF_FILENAME = "dtfs-ni-hadoop-site.xml";
    private static final String ZOOKEEPER_CONF_FILENAME = "dtfs-ni-zookeeper-site.xml";

    private static final String HDFS_CONF_FILENAME = "hdfs-site.xml";
    private static final String HBASE_CONF_FILENAME = "hbase-site.xml";

    static {
        CONF.addResource(COMMON_CONF_FILENAME);
        CONF.addResource(AMBARI_CONF_FILENAME);
        CONF.addResource(KAFKA_CONF_FILENAME);
        CONF.addResource(REST_CONF_FILENAME);
        CONF.addResource(HDFS_CONF_FILENAME);
        CONF.addResource(HBASE_CONF_FILENAME);
        CONF.addResource(ZOOKEEPER_CONF_FILENAME);
        CONF.addResource(HADOOP_CONF_FILENAME);
    }

    private Configs() {
        // Do nothing.
    }

    private static Configuration getConf() {
        return CONF;
    }

    public static String get(String key) {
        return CONF.getTrimmed(key);
    }

    public static String get(String key, String defaultValue) {
        return getConf().getTrimmed(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        return getConf().getInt(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getConf().getBoolean(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue) {
        return getConf().getLong(key, defaultValue);
    }

    /**
     * For class HBaseUtils.
     *
     * @return
     */
    public static String getHBaseConfFileName() {
        return HBASE_CONF_FILENAME;
    }

}
