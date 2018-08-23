package org.exp.dtfs.ni.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.HBaseConfigs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class HBaseUtils {
    private static final Log LOG = LogFactory.getLog(HBaseUtils.class);

    private static final String ZNODE_PARENT = HBaseConfigs.getZNodeParent();
    private static final String RS_ZNODE = "rs";

    private static final String SERVICE_NAME = "HBASE";
    private static final String RS_COMP_NAME = "HBASE_REGIONSERVER";

    private static final String HOST_ROLES_KEY = "HostRoles";
    private static final String HOST_COMPS_KEY = "host_components";
    private static final String HOST_NAME_KEY = "host_name";

    private HBaseUtils() {
        // Do nothing.
    }

    private static JSONObject getRegionServerServiceComponentKeyResponse(String key) throws URISyntaxException, IOException {
        String metricStr = AmbariRESTUtils.getServiceComponentMetrics(SERVICE_NAME, RS_COMP_NAME, key);
        return null == metricStr ? null : JSONObject.parseObject(metricStr);
    }

    public static List<String> listRegionServerHostnames() throws URISyntaxException, IOException {
        JSONObject response = getRegionServerServiceComponentKeyResponse(
                HOST_COMPS_KEY + Constants.SLASH_DELIMITER + HOST_ROLES_KEY + Constants.SLASH_DELIMITER + HOST_NAME_KEY);
        if (null == response) {
            return null;
        }
        List<String> rss = new Vector<>();
        JSONArray array = response.getJSONArray(HOST_COMPS_KEY);
        array.parallelStream().forEach(new Consumer<Object>() {
            @Override
            public void accept(Object t) {
                if (!(t instanceof JSONObject)) {
                    return;
                }
                Object hostname = ((JSONObject) t).getJSONObject(HOST_ROLES_KEY).get(HOST_NAME_KEY);
                if (null == hostname) {
                    return;
                }
                rss.add(hostname.toString());
            }
        });
        return rss;
    }

    public static boolean checkRegionServerAlive(String ip) throws Exception {
        LOG.info("Check if HBase RegionServer is alive, host is [" + ip + "].");
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

        String hostname = InetAddress.getByName(ip).getCanonicalHostName();
        LOG.info("Check RegionServer alive status, hostname is [" + hostname + "].");
        for (String rs : rss) {
            if (rs.startsWith(hostname)) {
                return true;
            }
        }
        return false;
    }

}
