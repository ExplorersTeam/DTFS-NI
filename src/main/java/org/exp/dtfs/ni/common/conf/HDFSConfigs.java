package org.exp.dtfs.ni.common.conf;

import org.exp.dtfs.ni.common.Constants;

/**
 *
 * HDFS configurations.
 *
 * @author ChenJintong
 *
 */
public class HDFSConfigs {
    private static final String NAMESERVICE_KEY = "dfs.nameservices";
    private static final String NN1_HTTP_ADDR_KEY = "dfs.namenode.http-address.ctdfs.nn1";
    private static final String NN2_HTTP_ADDR_KEY = "dfs.namenode.http-address.ctdfs.nn2";

    private HDFSConfigs() {
        // Do nothing.
    }

    public static String getNameService() {
        return Configs.get(NAMESERVICE_KEY, Constants.DEFAULT_SERVICE_NAME);
    }

    public static String getNameNode1HTTPAddr() {
        return Configs.get(NN1_HTTP_ADDR_KEY, Constants.DEFAULT_ADDR_STR);
    }

    public static int getNameNode1HTTPAddrPort() {
        return Integer.parseInt(getNameNode1HTTPAddr().split(Constants.COLON_DELIMITER)[1]);
    }

    public static String getNameNode2HTTPAddr() {
        return Configs.get(NN2_HTTP_ADDR_KEY, Constants.DEFAULT_ADDR_STR);
    }

    public static int getNameNode2HTTPAddrPort() {
        return Integer.parseInt(getNameNode2HTTPAddr().split(Constants.COLON_DELIMITER)[1]);
    }

}
