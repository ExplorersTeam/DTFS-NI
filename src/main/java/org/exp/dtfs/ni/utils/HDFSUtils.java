package org.exp.dtfs.ni.utils;

import java.io.IOException;
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
        int nn1Code = HTTPUtils.sendGETRequest(HTTPUtils.buildURI(host, HDFSConfigs.getNameNode1HTTPAddrPort()));
        int nn2Code = HTTPUtils.sendGETRequest(HTTPUtils.buildURI(host, HDFSConfigs.getNameNode2HTTPAddrPort()));
        LOG.info("Check network, NN1 status code is [" + nn1Code + "], NN2 status code is [" + nn2Code + "].");
        return !(HttpStatus.SC_OK != nn1Code && HttpStatus.SC_OK != nn2Code);
    }

}
