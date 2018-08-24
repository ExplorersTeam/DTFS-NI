package org.exp.dtfs.ni.test;

import java.net.URISyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exp.dtfs.ni.utils.HTTPUtils;
import org.junit.Test;

public class HTTPTest {
    private static final Log LOG = LogFactory.getLog(HTTPTest.class);

    @Test
    public static void main(String[] args) throws URISyntaxException {
        // System.out.println(HTTPUtils.buildURI("10.142.90.152", 8080,
        // "test").toString());
        LOG.info(HTTPUtils.buildURI("10.142.90.152", 8080, "test").toString());
    }

}
