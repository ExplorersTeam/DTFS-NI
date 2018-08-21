package org.exp.dtfs.ni.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HBaseUtils {
    private static final Log LOG = LogFactory.getLog(HBaseUtils.class);

    private HBaseUtils() {
        // Do nothing.
    }

    public static boolean checkRegionServerAliveStatus(String host) {
        LOG.info("Check if HBase Region Server is alive, host is [" + host + "].");
        // TODO Complete.
        return true;
    }

}
