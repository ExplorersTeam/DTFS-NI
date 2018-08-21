package org.exp.dtfs.ni.utils;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.message.BasicHeader;
import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.common.conf.AmbariConfigs;

/**
 * 
 * Operations to Ambari Server with RESTful APIs.
 * 
 * @author ChenJintong
 *
 */
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

    /*
     * Add component information into HTTP request path string.
     */
    private static String buildHTTPRequestPath(String path) {
        return new StringBuffer(API_PATH + Constants.SLASH_DELIMITER + V1_PATH + Constants.SLASH_DELIMITER + CLUSTERS_PATH)
                .append(path.startsWith(Constants.SLASH_DELIMITER) ? "" : Constants.SLASH_DELIMITER).append(path).toString();
    }

    public static String getMetrics(String path) throws URISyntaxException, IOException {
        return HTTPUtils.sendGETRequest(HTTPUtils.buildURI(AmbariConfigs.getServerIP(), AmbariConfigs.getServerPort(), path), new BasicHeader(AUTH_HEADER_KEY,
                AUTH_HEADER_VALUE_BASIC_PREFIX
                        + Base64.encodeBase64String((AmbariConfigs.getServerUserName() + Constants.COLON_DELIMITER + AmbariConfigs.getServerUserPassword())
                                .getBytes(Constants.DEFAULT_CHARSET))));
    }

    public static String getServiceComponentMetrics(String serviceName, String componentName) throws URISyntaxException, IOException {
        return getMetrics(
                buildHTTPRequestPath(HTTPUtils.buildRequetPath(AmbariConfigs.getClusterName(), SERVICES_PATH, serviceName, COMPONENTS_PATH, componentName)));
    }

}
