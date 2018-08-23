package org.exp.dtfs.ni.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.exp.dtfs.ni.common.Constants;

/**
 *
 * Operations through HTTP protocol.
 *
 * @author ChenJintong
 *
 */
public class HTTPUtils {
    private static final Log LOG = LogFactory.getLog(HTTPUtils.class);

    private static HttpClient client = HttpClientBuilder.create().build();

    private HTTPUtils() {
        // Do nothing.
    }

    /**
     * Build HTTP request path string.
     *
     * @param pathStrs
     * @return
     */
    public static String buildRequetPath(String... pathStrs) {
        StringBuffer buffer = new StringBuffer();
        for (String pathStr : pathStrs) {
            buffer.append(Constants.SLASH_DELIMITER).append(pathStr);
        }
        return buffer.toString();
    }

    /**
     * Build a HTTP URI with parameters.
     *
     * @param host
     * @param port
     * @param path
     * @param params
     * @return
     * @throws URISyntaxException
     */
    public static URI buildURI(String host, int port, String path, NameValuePair... params) throws URISyntaxException {
        URIBuilder builder = new URIBuilder();
        builder.setCharset(Constants.DEFAULT_CHARSET);
        builder.setScheme(Constants.HTTP_SHCEME);
        builder.setHost(host);
        builder.setPort(port);
        builder.setParameters(params);
        return null == path ? builder.build() : builder.setPath(path).build();
    }

    /**
     * Build a HTTP URI just with host address and port.
     *
     * @param host
     * @param port
     * @return
     * @throws URISyntaxException
     */
    public static URI buildURI(String host, int port) throws URISyntaxException {
        return buildURI(host, port, null);
    }

    /**
     * Send a HTTP GET request and get response body string.
     *
     * @param uri
     * @param headers
     * @return
     * @throws IOException
     */
    public static String sendGETRequest(URI uri, Header... headers) throws IOException {
        LOG.info("Send HTTP GET request, URI is [" + uri.toString() + "].");
        HttpGet get = new HttpGet();
        get.setURI(uri);
        for (Header header : headers) {
            get.setHeader(header);
        }
        HttpResponse response = client.execute(get);
        if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
            LOG.info("Connect to server failed.");
            return null;
        }
        String result = null;
        try (InputStream stream = response.getEntity().getContent()) {
            result = IOUtils.toString(stream, Constants.DEFAULT_CHARSET);
        }
        return result;
    }

    /**
     * Send a HTTP GET request and get status code.
     *
     * @param uri
     * @return
     * @throws IOException
     */
    public static int sendGETRequest(URI uri) throws IOException {
        LOG.info("Send HTTP GET request, URI is [" + uri.toString() + "].");
        HttpGet get = new HttpGet();
        get.setURI(uri);
        return client.execute(get).getStatusLine().getStatusCode();
    }

    /**
     * Send a HTTP GET request and get status code with host and port.
     * 
     * @param host
     * @param port
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static int sendGETRequest(String host, int port) throws IOException, URISyntaxException {
        LOG.info("Send HTTP GET request, host is [" + host + "], port is [" + port + "].");
        HttpGet get = new HttpGet();
        get.setURI(buildURI(host, port));
        return client.execute(get).getStatusLine().getStatusCode();
    }

}
