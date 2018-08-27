package org.exp.dtfs.ni.conf;

import org.exp.dtfs.ni.common.Constants;

public final class CommonConfigs {
    private static final String COMP_ID_KEY = "dtfs.ni.component.id";

    private static final String SEQ_ID_KEY = "dtfs.ni.seq.id";
    private static final String DEFAULT_SEQ_ID_VALUE = "100000001";

    private static final String LOG_PATH_KEY = "dtfs.ni.log.path";
    private static final String DEFAULT_LOG_PATH_VALUE = "/var/log/ambari-agent/ambari-agent.log";

    private CommonConfigs() {
        // DO nothing.
    }

    public static String getComponentID() {
        return Configs.get(COMP_ID_KEY, Constants.DEFAULT_SERVICE_NAME);
    }

    public static String getSeqID() {
        return Configs.get(SEQ_ID_KEY, DEFAULT_SEQ_ID_VALUE);
    }

    public static String getLogPath() {
        return Configs.get(LOG_PATH_KEY, DEFAULT_LOG_PATH_VALUE);
    }

}
