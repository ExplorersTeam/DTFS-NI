package org.exp.dtfs.ni.trash;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;

import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.utils.AmbariRESTUtils;

import com.alibaba.fastjson.JSONObject;

public class TmpUtils {
    private static final String SERVICE_NAME = "HDFS";
    private static final String DN_COMP_NAME = "DATANODE";
    private static final String SERVICE_COMP_INFO_KEY = "ServiceComponentInfo";

    private static final String METRICS_KEY = "metrics";
    private static final String DFS_KEY = "dfs";
    private static final String DATANODE_KEY = "datanode";
    private static final String READ_BLOCK_AVG_TIME_KEY = "readBlockOp_avg_time";
    private static final String WRITE_BLOCK_AVG_TIME_KEY = "writeBlockOp_avg_time";
    private static final String STARTED_COUNT_KEY = "started_count";
    private static final String UNKNOWN_COUNT_KEY = "unknown_count";

    private TmpUtils() {
        // Do nothing.
    }

    private static JSONObject getDataNodeServiceComponentKeyResponse(String key) throws URISyntaxException, IOException {
        String metricStr = AmbariRESTUtils.getServiceComponentMetrics(SERVICE_NAME, DN_COMP_NAME, key);
        return null == metricStr ? null : JSONObject.parseObject(metricStr);
    }

    /**
     * get DataNode average read time
     *
     * @param host
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */

    public static long getDataNodeAvgReadTime(String host) throws URISyntaxException, IOException {
        /*
         * Example.
         *
         * @URI
         * http://10.142.90.152:8080/api/v1/clusters/ctdfs/hosts/dfs1a1.ecld.com
         * /host_components/DATANODE?fields=metrics/dfs/datanode/
         * readBlockOp_avg_time
         *
         * @Reponse { "href" :
         * "http://10.142.90.152:8080/api/v1/clusters/ctdfs/hosts/dfs1a1.ecld.com/host_components/DATANODE?fields=metrics/dfs/datanode/readBlockOp_avg_time",
         * "HostRoles" : { "cluster_name" : "ctdfs", "component_name" :
         * "DATANODE", "host_name" : "dfs1a1.ecld.com" }, "host" : { "href" :
         * "http://10.142.90.152:8080/api/v1/clusters/ctdfs/hosts/dfs1a1.ecld.com"
         * }, "metrics" : { "dfs" : { "datanode" : { "readBlockOp_avg_time" :
         * 0.0 } } } }
         */
        String hostname = InetAddress.getByName(host).getCanonicalHostName();
        String response = AmbariRESTUtils.getHostComponentMetrics(hostname, DN_COMP_NAME, METRICS_KEY + Constants.SLASH_DELIMITER + DFS_KEY
                + Constants.SLASH_DELIMITER + DATANODE_KEY + Constants.SLASH_DELIMITER + READ_BLOCK_AVG_TIME_KEY);
        if (null == response) {
            return 0L;
        }
        Object num = JSONObject.parseObject(response).getJSONObject(METRICS_KEY).getJSONObject(DFS_KEY).getJSONObject(DATANODE_KEY)
                .get(READ_BLOCK_AVG_TIME_KEY);
        return null == num ? 0L : Long.parseLong(num.toString());
    }

    /**
     * get DataNode average write time
     *
     * @param host
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */

    public static long getDataNodeAvgWriteTime(String host) throws URISyntaxException, IOException {
        /*
         * Example.
         *
         * @URI
         * http://10.142.90.152:8080/api/v1/clusters/ctdfs/hosts/dfs1a1.ecld.com
         * /host_components/DATANODE?fields=metrics/dfs/datanode/
         * writeBlockOp_avg_time
         *
         * @Reponse { "href" :
         * "http://10.142.90.152:8080/api/v1/clusters/ctdfs/hosts/dfs1a1.ecld.com/host_components/DATANODE?fields=metrics/dfs/datanode/writeBlockOp_avg_time",
         * "HostRoles" : { "cluster_name" : "ctdfs", "component_name" :
         * "DATANODE", "host_name" : "dfs1a1.ecld.com" }, "host" : { "href" :
         * "http://10.142.90.152:8080/api/v1/clusters/ctdfs/hosts/dfs1a1.ecld.com"
         * }, "metrics" : { "dfs" : { "datanode" : { "writeBlockOp_avg_time" :
         * 13.0 } } } }
         */
        String hostname = InetAddress.getByName(host).getCanonicalHostName();
        String response = AmbariRESTUtils.getHostComponentMetrics(hostname, DN_COMP_NAME, METRICS_KEY + Constants.SLASH_DELIMITER + DFS_KEY
                + Constants.SLASH_DELIMITER + DATANODE_KEY + Constants.SLASH_DELIMITER + WRITE_BLOCK_AVG_TIME_KEY);
        if (null == response) {
            return 0L;
        }
        Object num = JSONObject.parseObject(response).getJSONObject(METRICS_KEY).getJSONObject(DFS_KEY).getJSONObject(DATANODE_KEY)
                .get(WRITE_BLOCK_AVG_TIME_KEY);
        return null == num ? 0L : Long.parseLong(num.toString());
    }

    public static int getActiveDataNodeNum() throws URISyntaxException, IOException {
        /*
         * Example.
         *
         * @URI http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/
         * components/DATANODE?fields=ServiceComponentInfo/started_count
         *
         * * @Reponse { "href" :
         * "http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/components/DATANODE?fields=ServiceComponentInfo/started_count",
         * "ServiceComponentInfo" : { "cluster_name" : "ctdfs", "component_name"
         * : "DATANODE", "service_name" : "HDFS", "started_count" : 3 } }
         */
        JSONObject response = getDataNodeServiceComponentKeyResponse(SERVICE_COMP_INFO_KEY + Constants.SLASH_DELIMITER + STARTED_COUNT_KEY);
        if (null == response) {
            return 0;
        }
        Object num = response.getJSONObject(SERVICE_COMP_INFO_KEY).get(STARTED_COUNT_KEY);
        return null == num ? 0 : Integer.parseInt(num.toString());
    }

    public static int getAbnormalDataNodeNum() throws URISyntaxException, IOException {
        /*
         * Example.
         *
         * @URI http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/
         * components/DATANODE?fields=ServiceComponentInfo/unknown_count
         *
         * * @Reponse { "href" :
         * "http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/components/DATANODE?fields=ServiceComponentInfo/unknown_count",
         * "ServiceComponentInfo" : { "cluster_name" : "ctdfs", "component_name"
         * : "DATANODE", "service_name" : "HDFS", "unknown_count" : 0 } }
         */
        JSONObject response = getDataNodeServiceComponentKeyResponse(SERVICE_COMP_INFO_KEY + Constants.SLASH_DELIMITER + UNKNOWN_COUNT_KEY);
        if (null == response) {
            return 0;
        }
        Object num = response.getJSONObject(SERVICE_COMP_INFO_KEY).get(UNKNOWN_COUNT_KEY);
        return null == num ? 0 : Integer.parseInt(num.toString());
    }
}
