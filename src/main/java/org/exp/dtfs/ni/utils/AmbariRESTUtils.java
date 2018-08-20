package org.exp.dtfs.ni.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.message.BasicHeader;
import org.exp.dtfs.ni.common.AmbariConfigs;
import org.exp.dtfs.ni.common.Constants;

public class AmbariRESTUtils {
    private static final String API_PATH = "api";
    private static final String V1_PATH = "v1";
    private static final String CLUSTERS_PATH = "clusters";
    private static final String SERVICES_PATH = "services";
    private static final String COMPONENTS_PATH = "components";

    private static final String AUTH_HEADER_KEY = "Authorization";
    private static final String AUTH_HEADER_VALUE_BASIC_PREFIX = "Basic ";

    private AmbariRESTUtils() {
        // Do nothing.
    }

    public static String getServiceComponentMetrics(String serviceName, String componentName) throws URISyntaxException, IOException {
        String path = HTTPUtils.buildRequetPath(API_PATH, V1_PATH, CLUSTERS_PATH, AmbariConfigs.getClusterName(), SERVICES_PATH, serviceName, COMPONENTS_PATH,
                componentName);
        URI uri = HTTPUtils.buildURI(AmbariConfigs.getServerIP(), AmbariConfigs.getServerPort(), path);
        return HTTPUtils.sendGETRequest(uri, new BasicHeader(AUTH_HEADER_KEY, AUTH_HEADER_VALUE_BASIC_PREFIX + Base64.encodeBase64String(
                (AmbariConfigs.getServerUserName() + Constants.COLON_DELIMITER + AmbariConfigs.getServerUserPassword()).getBytes(Constants.DEFAULT_CHARSET))));
    }

}
