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

    private static final String NAMESERVICE_KEY = "dfs.nameservices";
    private static final String NN1_HTTP_ADDR_KEY = "dfs.namenode.http-address." + getNameService() + ".nn1";
    private static final String NN2_HTTP_ADDR_KEY = "dfs.namenode.http-address." + getNameService() + ".nn2";

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

    public static String getNameNode1HTTPAddrHostname() {
        return getNameNode1HTTPAddr().split(Constants.COLON_DELIMITER)[0];
    }

    public static int getNameNode1HTTPAddrPort() {
        return Integer.parseInt(getNameNode1HTTPAddr().split(Constants.COLON_DELIMITER)[1]);
    }

    public static String getNameNode2HTTPAddr() {
        String addr = Configs.get(NN2_HTTP_ADDR_KEY, Constants.DEFAULT_ADDR_STR);
        LOG.info("HDFS NN2 HTTP address configuration item key is [" + NN2_HTTP_ADDR_KEY + "], result  is [" + addr + "].");
        return addr;
    }

    public static String getNameNode2HTTPAddrHostname() {
        return getNameNode2HTTPAddr().split(Constants.COLON_DELIMITER)[0];
    }

    public static int getNameNode2HTTPAddrPort() {
        return Integer.parseInt(getNameNode2HTTPAddr().split(Constants.COLON_DELIMITER)[1]);
    }

}
