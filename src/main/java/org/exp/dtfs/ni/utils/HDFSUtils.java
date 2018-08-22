package org.exp.dtfs.ni.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.HDFSConfigs;

public class HDFSUtils {
    private static final Log LOG = LogFactory.getLog(HDFSUtils.class);

    private HDFSUtils() {
        // Do nothing.
    }

    public static boolean checkNameNode1Alive(String host) throws NumberFormatException, IOException, URISyntaxException {
        LOG.info("Check if HDFS NameNode1 is alive, host is [" + host + "].");
        String[] nn1 = HDFSConfigs.getNameNode1HTTPAddr().split(Constants.COLON_DELIMITER);
        if (InetAddress.getByName(host).getCanonicalHostName().equals(nn1[0])) {
            return HttpStatus.SC_OK == HTTPUtils.sendGETRequest(HTTPUtils.buildURI(host, Integer.parseInt(nn1[1])));
        } else {
            throw new IllegalArgumentException("This host is not HDFS NameNode1.");
        }
    }

    public static boolean checkNameNode2Alive(String host) throws NumberFormatException, IOException, URISyntaxException {
        LOG.info("Check if HDFS NameNode2 is alive, host is [" + host + "].");
        String[] nn2 = HDFSConfigs.getNameNode2HTTPAddr().split(Constants.COLON_DELIMITER);
        if (InetAddress.getByName(host).getCanonicalHostName().equals(nn2[0])) {
            return HttpStatus.SC_OK == HTTPUtils.sendGETRequest(HTTPUtils.buildURI(host, Integer.parseInt(nn2[1])));
        } else {
            throw new IllegalArgumentException("This host is not HDFS NameNode2.");
        }
    }

    public static boolean checkNameNodeAlive(String host) throws IOException, URISyntaxException {
        LOG.info("Check if HDFS NameNode is alive, host is [" + host + "].");
        String[] nn1 = HDFSConfigs.getNameNode1HTTPAddr().split(Constants.COLON_DELIMITER);
        String[] nn2 = HDFSConfigs.getNameNode2HTTPAddr().split(Constants.COLON_DELIMITER);
        LOG.info("HDFS NameNode hostnames are [" + nn1[0] + "] and [" + nn2[0] + "].");
        if (InetAddress.getByName(host).getCanonicalHostName().equals(nn1[0])) {
            return HttpStatus.SC_OK == HTTPUtils.sendGETRequest(HTTPUtils.buildURI(host, Integer.parseInt(nn1[1])));
        } else if (InetAddress.getByName(host).getCanonicalHostName().equals(nn2[0])) {
            return HttpStatus.SC_OK == HTTPUtils.sendGETRequest(HTTPUtils.buildURI(host, Integer.parseInt(nn2[1])));
        } else {
            throw new IllegalArgumentException("This host is not HDFS NameNode.");
        }
    }

}
