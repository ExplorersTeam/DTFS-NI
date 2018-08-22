package org.exp.dtfs.ni.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonTest {
    private static final Log LOG = LogFactory.getLog(CommonTest.class);

    private CommonTest() {
        // Do nothing.
    }

    private static void test(String... strs) {
        for (String str : strs) {
            // System.out.println("[" + str + "]");
            LOG.info("[" + str + "]");
        }
    }

    public static void main(String[] args) {
        test();
        test(new String[] {});
        test("test");
    }

}
