package org.exp.dtfs.ni.report;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exp.dtfs.ni.report.thread.HDFSNameNodeProcessReportThread;
import org.exp.dtfs.ni.report.thread.HDFSNameNodeRPCClientConnectionNumberReportThread;
import org.exp.dtfs.ni.report.thread.HDFSNameNodeRoleReportThread;
import org.exp.dtfs.ni.report.thread.HDFSNameNodeSafeModeReportThread;

public class MetricReporter {
    private static final Log LOG = LogFactory.getLog(MetricReporter.class);

    private ScheduledExecutorService manager = Executors.newScheduledThreadPool(17);

    public MetricReporter() {
        /*
         * @Type 基础运行类
         *
         * @Name 数据管理节点服务进程是否存在
         *
         * @Comment 检查进程，如果进程不在，则产生告警
         *
         * @Period 1分钟
         */
        manager.scheduleAtFixedRate(new HDFSNameNodeProcessReportThread(), 0, 1, TimeUnit.MINUTES);

        /*
         * @Type 基础运行类
         *
         * @Name NameNode连接状态是否正常
         *
         * @Comment 检查NameNode是否正常，是否可以连接
         *
         * @Period 1分钟
         */
        manager.scheduleAtFixedRate(new HDFSNameNodeProcessReportThread(), 0, 1, TimeUnit.MINUTES);

        /*
         * @Type 基础运行类
         *
         * @Name NameNode是否启用安全模式
         *
         * @Comment 检查NameNode是否处于安全模式，启用安全模式说明不能写入
         *
         * @Period 1分钟
         */
        manager.scheduleAtFixedRate(new HDFSNameNodeSafeModeReportThread(), 0, 1, TimeUnit.MINUTES);

        /*
         * @Type 基础运行类
         *
         * @Name NameNode主备角色
         *
         * @Comment 检查NameNode主备状态(master：主，slave：备)
         *
         * @Period 1分钟
         */
        manager.scheduleAtFixedRate(new HDFSNameNodeRoleReportThread(), 0, 1, TimeUnit.MINUTES);

        /*
         * @Type 基础运行类
         *
         * @Name NameNode客户端连接数
         *
         * @Comment 获取NameNode当前的连接数
         *
         * @Period 1分钟
         */
        manager.scheduleAtFixedRate(new HDFSNameNodeRPCClientConnectionNumberReportThread(), 0, 1, TimeUnit.MINUTES);
    }

    public static void main(String[] args) {
        LOG.info("Start metrics reporter.");
        new MetricReporter();
    }

}
