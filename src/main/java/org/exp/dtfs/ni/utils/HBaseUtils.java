package org.exp.dtfs.ni.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.Configs;
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

    private static final Configuration CONF = HBaseConfiguration.create();

    static {
        CONF.addResource(Configs.getHBaseConfFileName());
    }

    private HBaseUtils() {
        // Do nothing.
    }

    private static Configuration getConf() {
        return CONF;
    }

    private static JSONObject getRegionServerServiceComponentKeyResponse(String key) throws URISyntaxException, IOException {
        String metricStr = AmbariRESTUtils.getServiceComponentMetrics(SERVICE_NAME, RS_COMP_NAME, key);
        return null == metricStr ? null : JSONObject.parseObject(metricStr);
    }

    public static int getAbnormalRegionServerNumber() throws Exception {
        return getResionServerNumber() - listAliveRegionServerInfos().size();
    }

    public static int getResionServerNumber() throws URISyntaxException, IOException {
        JSONObject response = getRegionServerServiceComponentKeyResponse(
                HOST_COMPS_KEY + Constants.SLASH_DELIMITER + HOST_ROLES_KEY + Constants.SLASH_DELIMITER + HOST_NAME_KEY);
        if (null == response) {
            return 0;
        }
        return response.getJSONArray(HOST_COMPS_KEY).size();
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

    public static List<String> listAliveRegionServerInfos() throws Exception {
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
        for (String rs : listAliveRegionServerInfos()) {
            if (rs.startsWith(hostname)) {
                return true;
            }
        }
        return false;
    }

    public static List<Result> searchEqual(String tableName, String family, String qualifier, String key) throws IOException {
        try (Connection connection = ConnectionFactory.createConnection(getConf()); Table table = connection.getTable(TableName.valueOf(tableName))) {
            Filter filter = new SingleColumnValueFilter(family.getBytes(), qualifier.getBytes(), CompareOperator.EQUAL, new BinaryComparator(key.getBytes()));
            Scan scan = new Scan();
            scan.setFilter(filter);
            ResultScanner scanner = table.getScanner(scan);
            List<Result> result = new Vector<>();
            scanner.forEach(new Consumer<Result>() {
                @Override
                public void accept(Result t) {
                    result.add(t);
                }
            });
            scanner.close();
            return result;
        }
    }

}
