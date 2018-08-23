package org.exp.dtfs.ni.utils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.HBaseConfigs;

public class DTFSUtils {
    private DTFSUtils() {
        // Do nothing.
    }

    public static Map<String, AtomicInteger> fileTypeCount() throws IOException {
        Map<String, AtomicInteger> ftc = new ConcurrentHashMap<>();
        AtomicInteger tinyNum = new AtomicInteger(0);
        AtomicInteger totalNum = new AtomicInteger(0);

        /*
         * Operations to HBase.
         */
        Configuration hbaseConf = HBaseConfiguration.create();
        hbaseConf.addResource("hbase-site.xml");
        Connection connection = ConnectionFactory.createConnection(hbaseConf);
        Table fileTable = connection.getTable(TableName.valueOf(HBaseConfigs.getDTFSFileTableName()));
        Scan scan = new Scan();
        // TODO Use filter to complete this work.

        ftc.put(Constants.TINY_FILE_KEY, tinyNum);
        ftc.put(Constants.TOTAL_FILE_KEY, totalNum);
        return ftc;
    }

}
