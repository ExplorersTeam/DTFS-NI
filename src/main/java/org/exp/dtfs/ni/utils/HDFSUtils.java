package org.exp.dtfs.ni.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.exp.dtfs.ni.conf.HDFSConfigs;

public class HDFSUtils {
    private static final Log LOG = LogFactory.getLog(HDFSUtils.class);

    private HDFSUtils() {
        // Do nothing.
    }

    public static boolean checkNameNodeAlive(URI uri) throws IOException {
        LOG.info("Check if HDFS NameNode is alive, URI is [" + uri.toString() + "].");
        return HttpStatus.SC_OK == HTTPUtils.sendGETRequest(uri);
    }

    public static boolean checkNameNodeAlive(String host) throws IOException, URISyntaxException {
        LOG.info("Check if HDFS NameNode is alive, host is [" + host + "].");
        if (InetAddress.getByName(host).getCanonicalHostName().equals(HDFSConfigs.getNameNode1HTTPAddrHostname())) {
            return HttpStatus.SC_OK == HTTPUtils.sendGETRequest(HTTPUtils.buildURI(host, HDFSConfigs.getNameNode1HTTPAddrPort()));
        } else if (InetAddress.getByName(host).getCanonicalHostName().equals(HDFSConfigs.getNameNode2HTTPAddrHostname())) {
            return HttpStatus.SC_OK == HTTPUtils.sendGETRequest(HTTPUtils.buildURI(host, HDFSConfigs.getNameNode2HTTPAddrPort()));
        } else {
            return false;
        }
    }

}
