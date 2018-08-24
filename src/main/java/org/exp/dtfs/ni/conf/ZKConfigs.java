package org.exp.dtfs.ni.conf;

public final class ZKConfigs {
    private static final String ZK_CONN_TIMEOUT_MS_KEY = "dtfs.ni.zookeeper.connection.timeout.ms";
    private static final int DEFAULT_ZK_CONN_TIMEOUT_MS_VALUE = 30000;

    private static final String ZK_RETRIES_MAX_KEY = "dtfs.ni.zookeeper.retries.max";
    private static final int DEFAULT_ZK_RETRIES_MAX_VALUE = 3;

    private static final String ZK_RETRY_EBOP_BASE_SLEEP_TIME_MS_KEY = "dtfs.ni.zookeeper.retry.exponential-back-off.sleep-time.base.ms";
    private static final int DEFAULT_ZK_RETRY_EBOP_BASE_SLEEP_TIME_MS_VALUE = 1000;

    private static final String ZK_RETRY_EBOP_MAX_SLEEP_TIME_MS_KEY = "dtfs.ni.zookeeper.retry.exponential-back-off.sleep-time.max.ms";
    private static final int DEFAULT_ZK_RETRY_EBOP_MAX_SLEEP_TIME_MS_VALUE = 1000;

    private ZKConfigs() {
        // Do nothing.
    }

    public static int getZKConnectionTimeoutMS() {
        return Configs.getInt(ZK_CONN_TIMEOUT_MS_KEY, DEFAULT_ZK_CONN_TIMEOUT_MS_VALUE);
    }

    public static int getZKMaxRetries() {
        return Configs.getInt(ZK_RETRIES_MAX_KEY, DEFAULT_ZK_RETRIES_MAX_VALUE);
    }

    public static int getZKExponentialBackoffRetryBaseSleepTimeMS() {
        return Configs.getInt(ZK_RETRY_EBOP_BASE_SLEEP_TIME_MS_KEY, DEFAULT_ZK_RETRY_EBOP_BASE_SLEEP_TIME_MS_VALUE);
    }

    public static int getZKExponentialBackoffRetryMaxSleepTimeMS() {
        return Configs.getInt(ZK_RETRY_EBOP_MAX_SLEEP_TIME_MS_KEY, DEFAULT_ZK_RETRY_EBOP_MAX_SLEEP_TIME_MS_VALUE);
    }

}
