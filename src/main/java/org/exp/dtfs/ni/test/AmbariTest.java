package org.exp.dtfs.ni.test;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exp.dtfs.ni.utils.AmbariRESTUtils;

public class AmbariTest {
    private static final Log LOG = LogFactory.getLog(AmbariTest.class);

    private AmbariTest() {
        // Do nothing.
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        LOG.info(AmbariRESTUtils.getServiceComponentMetrics(args[0], args[1]));
        // LOG.info(AmbariUtils.getClusterMetrics(args[0]));
    }
}
