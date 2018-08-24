package org.exp.dtfs.ni.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.HBaseConfigs;

public class DTFSUtils {
    private static final int TINY_SIZE = 2 * 1024 * 1024;

    private DTFSUtils() {
        // Do nothing.
    }

    public static Map<String, AtomicInteger> fileTypeCount() throws IOException {
        Map<String, AtomicInteger> ftc = new ConcurrentHashMap<>();
        AtomicInteger tinyNum = new AtomicInteger(0);

        String family = HBaseConfigs.getDTFSFileTableFamilyName();
        List<Result> fileResults = HBaseUtils.searchEqual(HBaseConfigs.getDTFSFileTableName(), family, HBaseConfigs.getDTFSFileTableDirColumnName(),
                Boolean.toString(false));
        fileResults.parallelStream().forEach(new Consumer<Result>() {
            @Override
            public void accept(Result t) {
                long size = Bytes.toLong(t.getValue(family.getBytes(), HBaseConfigs.getDTFSFileTableSizeColumnName().getBytes()));
                if (size <= TINY_SIZE) {
                    tinyNum.incrementAndGet();
                }
            }
        });

        ftc.put(Constants.TINY_FILE_KEY, tinyNum);
        ftc.put(Constants.TOTAL_FILE_KEY, new AtomicInteger(fileResults.size()));
        return ftc;
    }

    public static float getTinyFilePercentage() throws IOException {
        Map<String, AtomicInteger> ftc = fileTypeCount();
        return ftc.getOrDefault(Constants.TINY_FILE_KEY, new AtomicInteger(0)).get() / ftc.getOrDefault(Constants.TOTAL_FILE_KEY, new AtomicInteger(1)).get();
    }

}
