package org.exp.dtfs.ni.common;

import org.apache.hadoop.conf.Configuration;

public class Configs {
    private static final Configuration CONF = new Configuration();

    /*
     * Configuration file name.
     */
    private static final String AMBARI_CONF_FILENAME = "dtfs-ni-ambari-site.xml";
    private static final String KAFKA_CONF_FILENAME = "dtfs-ni-kafka-site.xml";

    static {
        CONF.addResource(AMBARI_CONF_FILENAME);
        CONF.addResource(KAFKA_CONF_FILENAME);
    }

    private Configs() {
        // Do nothing.
    }

    private static Configuration getConf() {
        return CONF;
    }

    public static String get(String key, String defaultValue) {
        return getConf().getTrimmed(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        return getConf().getInt(key, defaultValue);
    }

}
