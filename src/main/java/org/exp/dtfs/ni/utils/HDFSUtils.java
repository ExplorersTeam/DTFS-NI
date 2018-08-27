package org.exp.dtfs.ni.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.HDFSConfigs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class HDFSUtils {
    private static final Log LOG = LogFactory.getLog(HDFSUtils.class);

    private static final String SERVICE_NAME = "HDFS";
    private static final String NN_COMP_NAME = "NAMENODE";
    private static final String DN_COMP_NAME = "DATANODE";

    private static final String STARTED_STATUS = "STARTED";

    private static final String SERVICE_COMP_INFO_KEY = "ServiceComponentInfo";
    private static final String METRICS_KEY = "metrics";
    private static final String HOST_ROLES_KEY = "HostRoles";
    private static final String STATE_KEY = "state";
    private static final String DFS_KEY = "dfs";
    private static final String FS_NAME_SYSTEM_KEY = "FSNamesystem";
    private static final String DATANODE_KEY = "datanode";
    private static final String STARTED_COUNT_KEY = "started_count";
    private static final String UNKNOWN_COUNT_KEY = "unknown_count";
    private static final String HOST_COMPS_KEY = "host_components";
    private static final String HOST_NAME_KEY = "host_name";

    private HDFSUtils() {
        // Do nothing.
    }

    private static JSONObject getDataNodeServiceComponentKeyResponse(String key) throws URISyntaxException, IOException {
        String metricStr = AmbariRESTUtils.getServiceComponentMetrics(SERVICE_NAME, DN_COMP_NAME, key);
        return null == metricStr ? null : JSONObject.parseObject(metricStr);
    }

    /**
     * Get DataNode average read time
     *
     * @param host
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */

    public static float getDataNodeAvgReadTime(String host) throws URISyntaxException, IOException {
        String readBlkAvgTimeKey = "readBlockOp_avg_time";
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
        String response = AmbariRESTUtils.getHostComponentMetrics(hostname, DN_COMP_NAME,
                METRICS_KEY + Constants.SLASH_DELIMITER + DFS_KEY + Constants.SLASH_DELIMITER + DATANODE_KEY + Constants.SLASH_DELIMITER + readBlkAvgTimeKey);
        if (null == response) {
            return 0;
        }
        Object num = JSONObject.parseObject(response).getJSONObject(METRICS_KEY).getJSONObject(DFS_KEY).getJSONObject(DATANODE_KEY).get(readBlkAvgTimeKey);
        return null == num ? 0 : Float.parseFloat(num.toString());
    }

    /**
     * Get DataNode average write time
     *
     * @param host
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */

    public static float getDataNodeAvgWriteTime(String host) throws URISyntaxException, IOException {
        String writeBlkAvgTimeKey = "writeBlockOp_avg_time";
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
        String response = AmbariRESTUtils.getHostComponentMetrics(hostname, DN_COMP_NAME,
                METRICS_KEY + Constants.SLASH_DELIMITER + DFS_KEY + Constants.SLASH_DELIMITER + DATANODE_KEY + Constants.SLASH_DELIMITER + writeBlkAvgTimeKey);
        if (null == response) {
            return 0;
        }
        Object num = JSONObject.parseObject(response).getJSONObject(METRICS_KEY).getJSONObject(DFS_KEY).getJSONObject(DATANODE_KEY).get(writeBlkAvgTimeKey);
        return null == num ? 0 : Float.parseFloat(num.toString());
    }

    public static int getAliveDataNodeNum() throws URISyntaxException, IOException {
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
    public static long getTotalFilesNum() throws URISyntaxException, IOException {
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
    public static int getRPCClientConnNum() throws URISyntaxException, IOException {
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
     * Get capacity usage(GB).
     *
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public static float getCapacityUsageGB() throws URISyntaxException, IOException {
        String usedKey = "CapacityUsed";
        /*
         * Example.
         *
         * @URI http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/
         * components/NAMENODE?fields=ServiceComponentInfo/CapacityUsed
         *
         * @Reponse { "href" :
         * "http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/components/NAMENODE?fields=ServiceComponentInfo/CapacityUsed",
         * "ServiceComponentInfo" : { "CapacityUsed" : 1432013767472,
         * "cluster_name" : "ctdfs", "component_name" : "NAMENODE",
         * "service_name" : "HDFS" } }
         */
        JSONObject response = getNameNodeServiceComponentKeyResponse(SERVICE_COMP_INFO_KEY + Constants.SLASH_DELIMITER + usedKey);
        if (null == response) {
            return 0;
        }
        Object used = response.getJSONObject(SERVICE_COMP_INFO_KEY).get(usedKey);
        return null == used ? 0 : Long.parseLong(used.toString()) / 1024 / 1024 / 1024;
    }

    /**
     * Get NameNode heap memory usage(MB).
     *
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public static float getHeapMemoryUsageMB() throws URISyntaxException, IOException {
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
        return null == hmu ? 0 : Long.parseLong(hmu.toString()) / 1024 / 1024;
    }

    /**
     * Get NameNode CPU usage.
     *
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public static float getCPUUsage() throws URISyntaxException, IOException {
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

    public static boolean checkNameNode1Alive() throws IOException, URISyntaxException {
        String[] nn1 = HDFSConfigs.getNameNode1HTTPAddr().split(Constants.COLON);
        return HttpStatus.SC_OK == HTTPUtils.sendGETRequest(nn1[0], Integer.parseInt(nn1[1]));
    }

    public static boolean checkNameNode2Alive() throws IOException, URISyntaxException {
        String[] nn2 = HDFSConfigs.getNameNode2HTTPAddr().split(Constants.COLON);
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

    /**
     * Get active NameNode hostname.
     *
     * @return
     * @throws Exception
     */
    public static String getActiveNameNodeHostname() throws Exception {
        String znodeContent = new String(ZKUtils.get(HDFSConfigs.getHAZNodePath())).split(" ")[0];
        String delimiter = HDFSConfigs.getNameService() + "nn";
        return znodeContent.substring(znodeContent.indexOf(delimiter) + delimiter.length() + 8);
    }

    public static int getNameNodeRPCPort(String hostname) {
        String[] nn1Addrs = HDFSConfigs.getNameNode1RPCAddr().split(Constants.COLON);
        String[] nn2Addrs = HDFSConfigs.getNameNode2RPCAddr().split(Constants.COLON);
        int port = 8020;
        if (hostname.equals(nn1Addrs[0])) {
            port = Integer.parseInt(nn1Addrs[1]);
        } else if (hostname.equals(nn2Addrs[0])) {
            port = Integer.parseInt(nn2Addrs[1]);
        }
        return port;
    }

    public static int getNameNodeHTTPPort(String hostname) {
        String[] nn1Addrs = HDFSConfigs.getNameNode1HTTPAddr().split(Constants.COLON);
        String[] nn2Addrs = HDFSConfigs.getNameNode2HTTPAddr().split(Constants.COLON);
        int port = 50070;
        if (hostname.equals(nn1Addrs[0])) {
            port = Integer.parseInt(nn1Addrs[1]);
        } else if (hostname.equals(nn2Addrs[0])) {
            port = Integer.parseInt(nn2Addrs[1]);
        }
        return port;
    }

    public static boolean checkDataNodeAlive(String host) throws IOException, URISyntaxException {
        LOG.info("Check if HDFS DataNode is alive, host is [" + host + "].");
        String hostname = InetAddress.getByName(host).getCanonicalHostName();
        String response = AmbariRESTUtils.getHostComponentMetrics(hostname, DN_COMP_NAME, HOST_ROLES_KEY + Constants.SLASH_DELIMITER + STATE_KEY);
        if (null == response) {
            return false;
        }
        Object state = JSONObject.parseObject(response).getJSONObject(HOST_ROLES_KEY).get(STATE_KEY);
        return STARTED_STATUS.equals(String.valueOf(state));
    }

    /**
     * Get total files number.
     *
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public static long getCorruptBlockNum() throws URISyntaxException, IOException {
        String numKey = "CorruptBlocks";
        /*
         * Example.
         *
         * @URI http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/
         * components/NAMENODE?fields=metrics/dfs/FSNamesystem/CorruptBlocks
         *
         * @Reponse { "href" :
         * "http://10.142.90.152:8080/api/v1/clusters/ctdfs/services/HDFS/components/NAMENODE?fields=metrics/dfs/FSNamesystem/CorruptBlocks",
         * "ServiceComponentInfo" : { "cluster_name" : "ctdfs", "component_name"
         * : "NAMENODE", "service_name" : "HDFS" }, "metrics" : { "dfs" : {
         * "FSNamesystem" : { "CorruptBlocks" : 0 } } } }
         */
        JSONObject response = getNameNodeServiceComponentKeyResponse(
                METRICS_KEY + Constants.SLASH_DELIMITER + DFS_KEY + Constants.SLASH_DELIMITER + FS_NAME_SYSTEM_KEY + Constants.SLASH_DELIMITER + numKey);
        if (null == response) {
            return 0;
        }
        Object num = response.getJSONObject(METRICS_KEY).getJSONObject(DFS_KEY).getJSONObject(FS_NAME_SYSTEM_KEY).get(numKey);
        return null == num ? 0 : Long.parseLong(num.toString());
    }

    /**
     * List DataNode hostnames.
     *
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public static List<String> listDataNodeHostnames() throws URISyntaxException, IOException {
        JSONObject response = getDataNodeServiceComponentKeyResponse(
                HOST_COMPS_KEY + Constants.SLASH_DELIMITER + HOST_ROLES_KEY + Constants.SLASH_DELIMITER + HOST_NAME_KEY);
        List<String> dns = new Vector<>();
        if (null == response) {
            return dns;
        }
        JSONArray array = response.getJSONArray(HOST_COMPS_KEY);
        array.parallelStream().forEach(t -> {
            if (!(t instanceof JSONObject)) {
                return;
            }
            Object hostname = ((JSONObject) t).getJSONObject(HOST_ROLES_KEY).get(HOST_NAME_KEY);
            if (null == hostname) {
                return;
            }
            dns.add(hostname.toString());
        });
        return dns;
    }

}
