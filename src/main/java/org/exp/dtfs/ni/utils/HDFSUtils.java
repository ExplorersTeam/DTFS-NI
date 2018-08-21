package org.exp.dtfs.ni.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.exp.dtfs.ni.common.conf.HDFSConfigs;

public class HDFSUtils {
    private static final Log LOG = LogFactory.getLog(HDFSUtils.class);

    private HDFSUtils() {
        // Do nothing.
    }

    public static boolean checkNameNodeAliveStatus(URI uri) throws IOException {
        LOG.info("Check HDFS NameNode is alive or not, URI is [" + uri.toString() + "].");
        return HttpStatus.SC_OK == HTTPUtils.sendGETRequest(uri);
    }

    public static boolean checkNameNodeAliveStatus(String host) throws IOException, URISyntaxException {
        LOG.info("Check HDFS NameNode is alive or not, host is [" + host + "].");
        return !(HttpStatus.SC_OK != HTTPUtils.sendGETRequest(HTTPUtils.buildURI(host, HDFSConfigs.getNameNode1HTTPAddrPort()))
                && HttpStatus.SC_OK != HTTPUtils.sendGETRequest(HTTPUtils.buildURI(host, HDFSConfigs.getNameNode2HTTPAddrPort())));
    }

}
