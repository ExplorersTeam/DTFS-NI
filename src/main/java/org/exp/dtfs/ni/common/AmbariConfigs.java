package org.exp.dtfs.ni.common;

import org.exp.dtfs.ni.utils.PropertiesAdmin;

/**
 * 
 * Ambari configurations.
 * 
 * @author ChenJintong
 *
 */
public class AmbariConfigs {
    private static final PropertiesAdmin PROPERTIES = new PropertiesAdmin("dtfs-ni-ambari.properties");

    private static final String SERVER_IP_KEY = "dtfs.ni.ambari.server.ip";
    private static final String DEFAULT_SERVER_IP_VALUE = "127.0.0.1";

    private static final String SERVER_PORT_KEY = "dtfs.ni.ambari.server.port";
    private static final String DEFAULT_SERVER_PORT_VALUE = "8080";

    private static final String SERVER_USER_NAME_KEY = "dtfs.ni.ambari.server.user.name";
    private static final String DEFAULT_SERVER_USER_NAME_VALUE = "admin";

    private static final String SERVER_USER_PASSWORD_KEY = "dtfs.ni.ambari.server.user.password";
    private static final String DEFAULT_SERVER_USER_PASSWORD_VALUE = "admin";

    private AmbariConfigs() {
        // Do nothing.
    }

    public static String getServerIP() {
        return PROPERTIES.get(SERVER_IP_KEY, DEFAULT_SERVER_IP_VALUE);
    }

    public static int getServerPort() {
        return Integer.parseInt(PROPERTIES.get(SERVER_PORT_KEY, DEFAULT_SERVER_PORT_VALUE));
    }

    public static String getServerUserName() {
        return PROPERTIES.get(SERVER_USER_NAME_KEY, DEFAULT_SERVER_USER_NAME_VALUE);
    }

    public static String getServerUserPassword() {
        return PROPERTIES.get(SERVER_USER_PASSWORD_KEY, DEFAULT_SERVER_USER_PASSWORD_VALUE);
    }

}
