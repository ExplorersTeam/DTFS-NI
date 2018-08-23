package org.exp.dtfs.ni.conf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exp.dtfs.ni.common.Constants;

/**
 *
 * HDFS configurations.
 *
 * @author ChenJintong
 *
 */
public class HDFSConfigs {
    private static final Log LOG = LogFactory.getLog(HDFSConfigs.class);

    private static final String HA_ZNODE_PARENT_KEY = "dtfs.ni.hadoop.ha.znode.parent";
    private static final String DEFAULT_HA_ZNODE_PARENT_VALUE = "/hadoop-ha";
    private static final String HA_ZNODE_ASL_PATH = "ActiveStandbyElectorLock";

    private static final String NAMESERVICE_KEY = "dfs.nameservices";
    private static final String NN1_HTTP_ADDR_KEY = "dfs.namenode.http-address." + getNameService() + ".nn1";
    private static final String NN2_HTTP_ADDR_KEY = "dfs.namenode.http-address." + getNameService() + ".nn2";
    private static final String NN1_RPC_ADDR_KEY = "dfs.namenode.rpc-address." + getNameService() + ".nn1";
    private static final String NN2_RPC_ADDR_KEY = "dfs.namenode.rpc-address." + getNameService() + ".nn2";

    private HDFSConfigs() {
        // Do nothing.
    }

    public static String getNameService() {
        String nameService = Configs.get(NAMESERVICE_KEY, Constants.DEFAULT_SERVICE_NAME);
        LOG.info("HDFS name service is [" + nameService + "].");
        return nameService;
    }

    public static String getNameNode1HTTPAddr() {
        String addr = Configs.get(NN1_HTTP_ADDR_KEY, Constants.DEFAULT_ADDR_STR);
        LOG.info("HDFS NN1 HTTP address configuration item key is [" + NN1_HTTP_ADDR_KEY + "], result  is [" + addr + "].");
        return addr;
    }

    public static String getNameNode2HTTPAddr() {
        String addr = Configs.get(NN2_HTTP_ADDR_KEY, Constants.DEFAULT_ADDR_STR);
        LOG.info("HDFS NN2 HTTP address configuration item key is [" + NN2_HTTP_ADDR_KEY + "], result  is [" + addr + "].");
        return addr;
    }

    public static String getNameNode1RPCAddr() {
        String addr = Configs.get(NN1_RPC_ADDR_KEY, Constants.DEFAULT_ADDR_STR);
        LOG.info("HDFS NN1 RPC address configuration item key is [" + NN1_RPC_ADDR_KEY + "], result  is [" + addr + "].");
        return addr;
    }

    public static String getNameNode2RPCAddr() {
        String addr = Configs.get(NN2_RPC_ADDR_KEY, Constants.DEFAULT_ADDR_STR);
        LOG.info("HDFS NN2 RPC address configuration item key is [" + NN2_RPC_ADDR_KEY + "], result  is [" + addr + "].");
        return addr;
    }

    public static String getHAZNodePath() {
        String parent = Configs.get(HA_ZNODE_PARENT_KEY, DEFAULT_HA_ZNODE_PARENT_VALUE);
        StringBuffer path = new StringBuffer();
        if (!parent.startsWith(Constants.SLASH_DELIMITER)) {
            path.append(Constants.SLASH_DELIMITER);
        }
        path.append(parent);
        if (!parent.endsWith(Constants.SLASH_DELIMITER)) {
            path.append(Constants.SLASH_DELIMITER);
        }
        return path.append(getNameService()).append(Constants.SLASH_DELIMITER).append(HA_ZNODE_ASL_PATH).toString();
    }

}
