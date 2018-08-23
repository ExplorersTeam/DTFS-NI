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

    public static List<String> listAliveRegionServers() throws Exception {
        StringBuffer path = new StringBuffer();
        if (!ZNODE_PARENT.startsWith(Constants.SLASH_DELIMITER)) {
            path.append(Constants.SLASH_DELIMITER);
        }
        path.append(ZNODE_PARENT);
        if (!ZNODE_PARENT.endsWith(Constants.SLASH_DELIMITER)) {
            path.append(Constants.SLASH_DELIMITER);
        }
        List<String> rss = ZKUtils.list(path.append(RS_ZNODE).toString());
        LOG.info("Now there are [" + rss.size() + "] HBase RegionServers alive.");
        return rss;
    }

    public static boolean checkRegionServerAlive(String ip) throws Exception {
        LOG.info("Check if HBase RegionServer is alive, host is [" + ip + "].");
        String hostname = InetAddress.getByName(ip).getCanonicalHostName();
        LOG.info("Check RegionServer alive status, hostname is [" + hostname + "].");
        for (String rs : listAliveRegionServers()) {
            if (rs.startsWith(hostname)) {
                return true;
            }
        }
        return false;
    }

}
