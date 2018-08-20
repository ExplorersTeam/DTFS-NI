package org.exp.dtfs.ni.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exp.dtfs.ni.utils.AmbariUtils;

public class AmbariTest {
    private static final Log LOG = LogFactory.getLog(AmbariTest.class);

    private AmbariTest() {
        // Do nothing.
    }

    public static void main(String[] args) {
        LOG.info(AmbariUtils.getClusterMetrics(args[0]));
    }
}
