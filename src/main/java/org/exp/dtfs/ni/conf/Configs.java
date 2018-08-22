package org.exp.dtfs.ni.conf;

import org.apache.hadoop.conf.Configuration;

public class Configs {
    private static final Configuration CONF = new Configuration();

    /*
     * Configuration file name.
     */
    private static final String AMBARI_CONF_FILENAME = "dtfs-ni-ambari-site.xml";
    private static final String KAFKA_CONF_FILENAME = "dtfs-ni-kafka-site.xml";
    private static final String REST_CONF_FILENAME = "dtfs-ni-rest-site.xml";
    // private static final String HADOOP_CONF_FILENAME =
    // "dtfs-ni-hadoop-site.xml";

    // private static final String HADOOP_CORE_CONF_FILENAME = "core-site.xml";
    private static final String HDFS_CONF_FILENAME = "hdfs-site.xml";
    // private static final String HBASE_CONF_FILENAME = "hbase-site.xml";

    /*
     * Hadoop configurations.
     */
    // private static final String HADOOP_CONF_MAIN_PATH_PARENT_KEY =
    // "dtfs.ni.hadoop.conf.main.path.parent";
    // private static final String HBASE_CONF_PATH_PARENT_KEY =
    // "dtfs.ni.hbase.conf.main.path.parent";

    static {
        CONF.addResource(AMBARI_CONF_FILENAME);
        CONF.addResource(KAFKA_CONF_FILENAME);
        CONF.addResource(REST_CONF_FILENAME);
        CONF.addResource(HDFS_CONF_FILENAME);
    }

    private Configs() {
        // Do nothing.
    }

    private static Configuration getConf() {
        return CONF;
    }

    // private static void addResources(String key, String... fileNames) {
    // String path = get(key);
    // LOG.info("Resource path is [" + path + "].");
    // if (null == path || null == fileNames || 0 >= fileNames.length) {
    // return;
    // }
    // for (String fileName : fileNames) {
    // LOG.info("Resource file name is [" + fileName + "].");
    // CONF.addResource(path + (path.endsWith(Constants.SLASH_DELIMITER) ? "" :
    // Constants.SLASH_DELIMITER) + fileName);
    // }
    // }

    public static String get(String key) {
        return CONF.getTrimmed(key);
    }

    public static String get(String key, String defaultValue) {
        return getConf().getTrimmed(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        return getConf().getInt(key, defaultValue);
    }

}
