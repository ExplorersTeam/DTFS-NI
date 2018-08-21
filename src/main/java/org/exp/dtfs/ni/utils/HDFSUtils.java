package org.exp.dtfs.ni.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpStatus;
import org.exp.dtfs.ni.common.conf.HDFSConfigs;

public class HDFSUtils {

    private HDFSUtils() {
        // Do nothing.
    }

    public static boolean checkNameNodeAliveStatus(URI uri) throws IOException {
        return HttpStatus.SC_OK == HTTPUtils.sendGETRequest(uri);
    }

    public static boolean checkNameNodeAliveStatus(String host) throws IOException, URISyntaxException {
        return !(HttpStatus.SC_OK != HTTPUtils.sendGETRequest(HTTPUtils.buildURI(host, HDFSConfigs.getNameNode1HTTPAddrPort()))
                && HttpStatus.SC_OK != HTTPUtils.sendGETRequest(HTTPUtils.buildURI(host, HDFSConfigs.getNameNode2HTTPAddrPort())));
    }

}
