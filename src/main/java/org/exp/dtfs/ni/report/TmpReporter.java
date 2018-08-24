package org.exp.dtfs.ni.report;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exp.dtfs.ni.report.thread.hdfs.HDFSTotalCorruptBlockNumberReportThread;

public class TmpReporter {

    private static final Log LOG = LogFactory.getLog(MetricReporter.class);

    private ScheduledExecutorService manager = Executors.newScheduledThreadPool(17);

    public TmpReporter() {
        // 基础运行类 - 集群中已损坏block总个数
        manager.scheduleAtFixedRate(new HDFSTotalCorruptBlockNumberReportThread(), 0, 1, TimeUnit.MINUTES);

    }

    private static enum PCommand {
        P_HB_DNREAD, // 性能类 - 单个数据节点的平均读取时间
        P_HB_DNWRITE, // 性能类 - 单个数据节点的平均写入时间
        P_HB_CLRMEM, // 基础运行类 - 集群占用内存总数

        P_HB_DNVALID, // 基础运行类 - 存活数据节点的个数
        P_HB_DNEXPNUM, // 基础运行类 - 异常数据节点的个数
        P_HB_CLRDISK, // 基础运行类 - 集群磁盘空间占用率
    }

}
