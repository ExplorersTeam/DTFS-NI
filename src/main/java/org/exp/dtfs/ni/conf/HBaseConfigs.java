package org.exp.dtfs.ni.conf;

import org.exp.dtfs.ni.common.Constants;

public class HBaseConfigs {
    private static final String ZNODE_PARENT_KEY = "zookeeper.znode.parent";
    private static final String ZK_QUORUM_KEY = "hbase.zookeeper.quorum";
    private static final String FILE_TABLE_NAME_KEY = "dtfs.ni.hbase.file.table.name";

    private static final String ZK_SESSION_TIMEOUT_KEY = "zookeeper.session.timeout";
    private static final int DEFAULT_ZK_SESSION_TIMEOUT_VALUE = 90000;

    private static final String RS_PORT_KEY = "hbase.regionserver.port";
    private static final int DEFAULT_RS_PORT_VALUE = 16020;

    private HBaseConfigs() {
        // Do nothing.
    }

    public static String getZNodeParent() {
        return Configs.get(ZNODE_PARENT_KEY, Constants.DEFAULT_SERVICE_NAME);
    }

    public static String getZKQuorum() {
        return Configs.get(ZK_QUORUM_KEY, Constants.DEFAULT_ADDR_STR);
    }

    public static int getZKSessionTimeoutMS() {
        return Configs.getInt(ZK_SESSION_TIMEOUT_KEY, DEFAULT_ZK_SESSION_TIMEOUT_VALUE);
    }

    public static String getDTFSFileTableName() {
        return Configs.get(FILE_TABLE_NAME_KEY, Constants.DEFAULT_SERVICE_NAME);
    }

    public static int getRegionServerPort() {
        return Configs.getInt(RS_PORT_KEY, DEFAULT_RS_PORT_VALUE);
    }

}
