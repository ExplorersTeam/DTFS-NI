package org.exp.dtfs.ni.conf;

import org.exp.dtfs.ni.common.Constants;

public final class CommonConfigs {
    private static final String COMP_ID_KEY = "dtfs.ni.component.id";

    private CommonConfigs() {
        // DO nothing.
    }

    public static String getComponentID() {
        return Configs.get(COMP_ID_KEY, Constants.DEFAULT_SERVICE_NAME);
    }

}
