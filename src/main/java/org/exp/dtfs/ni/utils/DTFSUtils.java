package org.exp.dtfs.ni.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.exp.dtfs.ni.common.Constants;

public class DTFSUtils {
    private DTFSUtils() {
        // Do nothing.
    }

    public static Map<String, AtomicInteger> fileTypeCount() {
        Map<String, AtomicInteger> ftc = new ConcurrentHashMap<>();
        AtomicInteger tinyNum = new AtomicInteger(0);
        AtomicInteger totalNum = new AtomicInteger(0);
        // TODO HBase table operations.
        ftc.put(Constants.TINY_FILE_KEY, tinyNum);
        ftc.put(Constants.TOTAL_FILE_KEY, totalNum);
        return ftc;
    }

}
