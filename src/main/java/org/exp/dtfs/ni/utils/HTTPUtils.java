package org.exp.dtfs.ni.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
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
    public static URI buildURI(String host, int port, String path, NameValuePair[] params) throws URISyntaxException {
        URIBuilder builder = new URIBuilder();
        builder.setCharset(Constants.DEFAULT_CHARSET);
        builder.setScheme(Constants.HTTP_SHCEME);
        builder.setHost(host);
        builder.setPort(port);
        if (null != path) {
            builder.setPath(path);
        }
        return (null == params ? builder.build() : builder.setParameters(params).build());
    }

    /**
     * Build a HTTP URI without parameter.
     * 
     * @param host
     * @param port
     * @param path
     * @return
     * @throws URISyntaxException
     */
    public static URI buildURI(String host, int port, String path) throws URISyntaxException {
        return buildURI(host, port, path, null);
    }

    /**
     * Send a HTTP GET request.
     * 
     * @param uri
     * @param headers
     * @return
     * @throws IOException
     */
    public static String sendGETRequest(URI uri, Header... headers) throws IOException {
        HttpGet get = new HttpGet();
        get.setURI(uri);
        if (null != headers) {
            for (Header header : headers) {
                get.setHeader(header);
            }
        }
        HttpResponse response = client.execute(get);
        String result = null;
        try (InputStream stream = response.getEntity().getContent()) {
            result = IOUtils.toString(stream, Constants.DEFAULT_CHARSET);
        }
        return result;
    }

}
