package org.exp.dtfs.ni.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.HDFSConfigs;

import com.alibaba.fastjson.JSONObject;

public class HDFSUtils {
    private static final Log LOG = LogFactory.getLog(HDFSUtils.class);

    private static final String SERVICE_NAME = "HDFS";
    private static final String NN_COMP_NAME = "NAMENODE";

    private static final String STARTED_STATUS = "STARTED";

    private static final String SERVICE_COMP_INFO_KEY = "ServiceComponentInfo";
    private static final String METRICS_KEY = "metrics";
    private static final String HOST_ROLES_KEY = "HostRoles";
    private static final String STATE_KEY = "state";

    private HDFSUtils() {
        // Do nothing.
    }

    private static JSONObject getNameNodeServiceComponentKeyResponse(String key) throws URISyntaxException, IOException {
        String metricStr = AmbariRESTUtils.getServiceComponentMetrics(SERVICE_NAME, NN_COMP_NAME, key);
        return null == metricStr ? null : JSONObject.parseObject(metricStr);
    }

    /**
     * Get total files number.
     * 
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public static long totalFilesNum() throws URISyntaxException, IOException {
        String numKey = "TotalFiles";
        /*
         * Example.
         *
         * @URI http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/
         * components/NAMENODE?fields=ServiceComponentInfo/TotalFiles
         *
         * @Reponse { "href" :
         * "http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/components/NAMENODE?fields=ServiceComponentInfo/TotalFiles",
         * "ServiceComponentInfo" : { "TotalFiles" : 1683, "cluster_name" :
         * "ctdfs", "component_name" : "NAMENODE", "service_name" : "HDFS" } }
         */
        JSONObject response = getNameNodeServiceComponentKeyResponse(SERVICE_COMP_INFO_KEY + Constants.SLASH_DELIMITER + numKey);
        if (null == response) {
            return 0;
        }
        Object num = response.getJSONObject(SERVICE_COMP_INFO_KEY).get(numKey);
        return null == num ? 0 : Long.parseLong(num.toString());
    }

    /**
     * Get RPC client connection number.
     *
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public static int rpcClientConnNum() throws URISyntaxException, IOException {
        String rpcKey = "rpc";
        String clientKey = "client";
        String numKey = "NumOpenConnections";
        /*
         * Example.
         *
         * @URI http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/
         * components/NAMENODE?fields=metrics/rpc/client/NumOpenConnections
         *
         * @Reponse { "href" :
         * "http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/components/NAMENODE?fields=metrics/rpc/client/NumOpenConnections",
         * "ServiceComponentInfo" : { "cluster_name" : "ctdfs", "component_name"
         * : "NAMENODE", "service_name" : "HDFS" }, "metrics" : { "rpc" : {
         * "client" : { "NumOpenConnections" : 4 } } } }
         */
        JSONObject response = getNameNodeServiceComponentKeyResponse(
                METRICS_KEY + Constants.SLASH_DELIMITER + rpcKey + Constants.SLASH_DELIMITER + clientKey + Constants.SLASH_DELIMITER + numKey);
        if (null == response) {
            return 0;
        }
        Object num = response.getJSONObject(METRICS_KEY).getJSONObject(rpcKey).getJSONObject(clientKey).get(numKey);
        return null == num ? 0 : Integer.parseInt(num.toString());
    }

    /**
     * Get capacity usage.
     *
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public static float capacityUsage() throws URISyntaxException, IOException {
        String totalKey = "CapacityTotal";
        String usedKey = "CapacityUsed";
        /*
         * Example.
         *
         * @URI http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/
         * components/NAMENODE?fields=ServiceComponentInfo
         *
         * @Reponse { "href" :
         * "http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/components/NAMENODE?fields=ServiceComponentInfo",
         * "ServiceComponentInfo" : { "CapacityRemaining" : 43524960768996,
         * "CapacityTotal" : 44991355305984, "CapacityUsed" : 1432005382144,
         * "DeadNodes" : "{}", "DecomNodes" : "{}", "HeapMemoryMax" :
         * 31809601536, "HeapMemoryUsed" : 2566347176, "LiveNodes" :
         * "{\"dfs1m1.ecld.com:1019\":{\"infoAddr\":\"10.142.90.153:1022\",\"infoSecureAddr\":\"10.142.90.153:0\",\"xferaddr\":\"10.142.90.153:1019\",\"lastContact\":1,\"usedSpace\":477335101440,\"adminState\":\"In Service\",\"nonDfsUsedSpace\":10314698420,\"capacity\":14247521370112,\"numBlocks\":4252,\"version\":\"2.7.3.2.5.3.0-37\",\"used\":477335101440,\"remaining\":13759871570252,\"blockScheduled\":0,\"blockPoolUsed\":477335101440,\"blockPoolUsedPercent\":3.3503027,\"volfails\":0},\"dfs1a1.ecld.com:1019\":{\"infoAddr\":\"10.142.90.152:1022\",\"infoSecureAddr\":\"10.142.90.152:0\",\"xferaddr\":\"10.142.90.152:1019\",\"lastContact\":1,\"usedSpace\":477335138304,\"adminState\":\"In Service\",\"nonDfsUsedSpace\":20315381428,\"capacity\":15371916967936,\"numBlocks\":4252,\"version\":\"2.7.3.2.5.3.0-37\",\"used\":477335138304,\"remaining\":14874266448204,\"blockScheduled\":0,\"blockPoolUsed\":477335138304,\"blockPoolUsedPercent\":3.1052413,\"volfails\":0},\"dfs1m2.ecld.com:1019\":{\"infoAddr\":\"10.142.90.154:1022\",\"infoSecureAddr\":\"10.142.90.154:0\",\"xferaddr\":\"10.142.90.154:1019\",\"lastContact\":1,\"usedSpace\":477335142400,\"adminState\":\"In Service\",\"nonDfsUsedSpace\":3759074996,\"capacity\":15371916967936,\"numBlocks\":4252,\"version\":\"2.7.3.2.5.3.0-37\",\"used\":477335142400,\"remaining\":14890822750540,\"blockScheduled\":0,\"blockPoolUsed\":477335142400,\"blockPoolUsedPercent\":3.1052413,\"volfails\":0}}"
         * , "NonDfsUsedSpace" : 34389154844, "NonHeapMemoryMax" : -1,
         * "NonHeapMemoryUsed" : 86055896, "PercentRemaining" : 96.740715,
         * "PercentUsed" : 3.1828456, "Safemode" : "", "StartTime" :
         * 1531462009655, "TotalFiles" : 1683, "UpgradeFinalized" : true,
         * "Version" :
         * "2.7.3.2.5.3.0-37, r9828acfdec41a121f0121f556b09e2d112259e92",
         * "category" : "MASTER", "cluster_name" : "ctdfs", "component_name" :
         * "NAMENODE", "display_name" : "NameNode", "init_count" : 0,
         * "install_failed_count" : 0, "installed_count" : 0, "recovery_enabled"
         * : "false", "service_name" : "HDFS", "started_count" : 2, "state" :
         * "STARTED", "total_count" : 2, "unknown_count" : 0 } }
         */
        JSONObject response = getNameNodeServiceComponentKeyResponse(SERVICE_COMP_INFO_KEY);
        if (null == response) {
            return 0;
        }
        JSONObject info = response.getJSONObject(SERVICE_COMP_INFO_KEY);
        Object total = info.get(totalKey);
        Object used = info.get(usedKey);
        return (null == total || null == used) ? 0 : Long.parseLong(used.toString()) / Long.parseLong(total.toString());
    }

    /**
     * Get NameNode heap memory usage.
     *
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public static long heapMemoryUsed() throws URISyntaxException, IOException {
        String heapMemUsedKey = "HeapMemoryUsed";
        /*
         * Example.
         *
         * @URI http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/
         * components/NAMENODE?fields=ServiceComponentInfo/HeapMemoryUsed
         *
         * @Reponse { "href" :
         * "http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/components/NAMENODE?fields=ServiceComponentInfo/HeapMemoryUsed",
         * "ServiceComponentInfo" : { "HeapMemoryUsed" : 2278014536,
         * "cluster_name" : "ctdfs", "component_name" : "NAMENODE",
         * "service_name" : "HDFS" } }
         */
        JSONObject response = getNameNodeServiceComponentKeyResponse(SERVICE_COMP_INFO_KEY + Constants.SLASH_DELIMITER + heapMemUsedKey);
        if (null == response) {
            return 0;
        }
        Object hmu = response.getJSONObject(SERVICE_COMP_INFO_KEY).get(heapMemUsedKey);
        return null == hmu ? 0 : Long.parseLong(hmu.toString());
    }

    /**
     * Get NameNode CPU usage.
     *
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public static float cpuUsage() throws URISyntaxException, IOException {
        String cpuKey = "cpu";
        String cpuSysKey = "cpu_system";
        /*
         * Example.
         *
         * @URI http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/
         * components/NAMENODE?fields=metrics/cpu/cpu_system
         *
         * @Reponse { "href" :
         * "http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/components/NAMENODE?fields=metrics/cpu/cpu_system",
         * "ServiceComponentInfo" : { "cluster_name" : "ctdfs", "component_name"
         * : "NAMENODE", "service_name" : "HDFS" }, "metrics" : { "cpu" : {
         * "cpu_system" : 0.35555555555555557 } } }
         */
        JSONObject response = getNameNodeServiceComponentKeyResponse(METRICS_KEY + Constants.SLASH_DELIMITER + cpuKey + Constants.SLASH_DELIMITER + cpuSysKey);
        if (null == response) {
            return 0;
        }
        Object cpuUsage = response.getJSONObject(METRICS_KEY).getJSONObject(cpuKey).get(cpuSysKey);
        return null == cpuUsage ? 0 : Float.parseFloat(cpuUsage.toString());
    }

    /**
     * Check if NameNode is in safe mode.
     *
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public static boolean isSafeMode() throws URISyntaxException, IOException {
        String safeModeKey = "Safemode";
        /*
         * Example.
         *
         * @URI http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/
         * components/NAMENODE?fields=ServiceComponentInfo/Safemode
         *
         * @Response { "href" :
         * "http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/components/NAMENODE?fields=ServiceComponentInfo/Safemode",
         * "ServiceComponentInfo" : { "Safemode" : "", "cluster_name" : "ctdfs",
         * "component_name" : "NAMENODE", "service_name" : "HDFS" } }
         */
        JSONObject response = getNameNodeServiceComponentKeyResponse(SERVICE_COMP_INFO_KEY + Constants.SLASH_DELIMITER + safeModeKey);
        if (null == response) {
            return false;
        }
        Object sm = response.getJSONObject(SERVICE_COMP_INFO_KEY).get(safeModeKey);
        LOG.info("Check if HDFS is in safe mode, response is [" + String.valueOf(sm) + "].");
        return !(null == sm || ((String) sm).isEmpty());
    }

    public static boolean checkNameNode1Alive() throws NumberFormatException, IOException, URISyntaxException {
        String[] nn1 = HDFSConfigs.getNameNode1HTTPAddr().split(Constants.COLON_DELIMITER);
        return HttpStatus.SC_OK == HTTPUtils.sendGETRequest(nn1[0], Integer.parseInt(nn1[1]));
    }

    public static boolean checkNameNode2Alive() throws NumberFormatException, IOException, URISyntaxException {
        String[] nn2 = HDFSConfigs.getNameNode2HTTPAddr().split(Constants.COLON_DELIMITER);
        return HttpStatus.SC_OK == HTTPUtils.sendGETRequest(nn2[0], Integer.parseInt(nn2[1]));
    }

    public static boolean checkNameNodeAlive(String host) throws IOException, URISyntaxException {
        LOG.info("Check if HDFS NameNode is alive, host is [" + host + "].");
        String hostname = InetAddress.getByName(host).getCanonicalHostName();
        String response = AmbariRESTUtils.getHostComponentMetrics(hostname, NN_COMP_NAME, HOST_ROLES_KEY + Constants.SLASH_DELIMITER + STATE_KEY);
        if (null == response) {
            return false;
        }
        Object state = JSONObject.parseObject(response).getJSONObject(HOST_ROLES_KEY).get(STATE_KEY);
        return STARTED_STATUS.equals(String.valueOf(state));
    }

}
