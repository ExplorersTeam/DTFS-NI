package org.exp.dtfs.ni.utils;

import java.io.IOException;
import java.net.URISyntaxException;

import com.alibaba.fastjson.JSONObject;

public class HostUtils {
    private static final String AMBARI_ITEMS_KEY = "items";

    private HostUtils() {
        // Do nothing.
    }

    public static int getClusterHostCount() throws URISyntaxException, IOException {
        return JSONObject.parseObject(AmbariRESTUtils.getHostsMetrics()).getJSONArray(AMBARI_ITEMS_KEY).size();
    }

}
