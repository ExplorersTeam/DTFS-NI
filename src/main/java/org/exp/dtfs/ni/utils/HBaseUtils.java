package org.exp.dtfs.ni.utils;

import java.net.InetAddress;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.HBaseConfigs;

public class HBaseUtils {
    private static final Log LOG = LogFactory.getLog(HBaseUtils.class);

    private static final String ZNODE_PARENT = HBaseConfigs.getZNodeParent();
    private static final String RS_ZNODE = "rs";

    private HBaseUtils() {
        // Do nothing.
    }

    public static boolean checkRegionServerAlive(String ip) throws Exception {
        LOG.info("Check if HBase RegionServer is alive, host is [" + ip + "].");
        String hostname = InetAddress.getByName(ip).getCanonicalHostName();
        LOG.info("Check RegionServer alive status, hostname is [" + hostname + "].");
        List<String> rss = ZKUtils.list(ZNODE_PARENT + (ZNODE_PARENT.endsWith(Constants.SLASH_DELIMITER) ? "" : Constants.SLASH_DELIMITER) + RS_ZNODE);
        LOG.info("Now there are [" + rss.size() + "] HBase RegionServers alive.");
        for (String rs : rss) {
            if (rs.startsWith(hostname)) {
                return true;
            }
        }
        return false;
    }

}
