package org.exp.dtfs.ni.conf;

public final class RESTConfigs {
    private static final String SERVER_PORT_KEY = "dtfs.ni.rest.server.port";
    private static final int DEFAULT_SERVER_PORT_VALUE = 8080;

    private RESTConfigs() {
        // Do nothing.
    }

    public static int getServerPort() {
        return Configs.getInt(SERVER_PORT_KEY, DEFAULT_SERVER_PORT_VALUE);
    }

}
