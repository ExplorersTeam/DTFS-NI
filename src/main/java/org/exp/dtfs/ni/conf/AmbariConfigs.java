package org.exp.dtfs.ni.conf;

import org.exp.dtfs.ni.common.Constants;

/**
 * 
 * Ambari configurations.
 * 
 * @author ChenJintong
 *
 */
public class AmbariConfigs {
    private static final String SERVER_IP_KEY = "dtfs.ni.ambari.server.ip";

    private static final String SERVER_PORT_KEY = "dtfs.ni.ambari.server.port";
    private static final int DEFAULT_SERVER_PORT_VALUE = 8080;

    private static final String SERVER_USER_NAME_KEY = "dtfs.ni.ambari.server.user.name";
    private static final String DEFAULT_SERVER_USER_NAME_VALUE = "admin";

    private static final String SERVER_USER_PASSWORD_KEY = "dtfs.ni.ambari.server.user.password";
    private static final String DEFAULT_SERVER_USER_PASSWORD_VALUE = "admin";

    private static final String CLUSTER_NAME_KEY = "dtfs.ni.ambari.cluster.name";

    private AmbariConfigs() {
        // Do nothing.
    }

    public static String getServerIP() {
        return Configs.get(SERVER_IP_KEY, Constants.DEFAULT_ADDR_STR);
    }

    public static int getServerPort() {
        return Configs.getInt(SERVER_PORT_KEY, DEFAULT_SERVER_PORT_VALUE);
    }

    public static String getServerUserName() {
        return Configs.get(SERVER_USER_NAME_KEY, DEFAULT_SERVER_USER_NAME_VALUE);
    }

    public static String getServerUserPassword() {
        return Configs.get(SERVER_USER_PASSWORD_KEY, DEFAULT_SERVER_USER_PASSWORD_VALUE);
    }

    public static String getClusterName() {
        return Configs.get(CLUSTER_NAME_KEY, Constants.DEFAULT_SERVICE_NAME);
    }

}
