package org.exp.dtfs.ni.report;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exp.dtfs.ni.report.thread.dtfs.DTFSTinyFilePercentageReportThread;
import org.exp.dtfs.ni.report.thread.hbase.HBaseRegionServerAbnormalNumberReportThread;
import org.exp.dtfs.ni.report.thread.hbase.HBaseRegionServerAliveNumberReportThread;
import org.exp.dtfs.ni.report.thread.hbase.HBaseRegionServerProcessReportThread;
import org.exp.dtfs.ni.report.thread.hdfs.HDFSDataNodeProcessReportThread;
import org.exp.dtfs.ni.report.thread.hdfs.HDFSDataNodeReadTimeReportThread;
import org.exp.dtfs.ni.report.thread.hdfs.HDFSDataNodeWriteTimeReportThread;
import org.exp.dtfs.ni.report.thread.hdfs.HDFSNameNodeCPUUsageReportThread;
import org.exp.dtfs.ni.report.thread.hdfs.HDFSNameNodeCapacityUsageReportThread;
import org.exp.dtfs.ni.report.thread.hdfs.HDFSNameNodeMemoryUsageReportThread;
import org.exp.dtfs.ni.report.thread.hdfs.HDFSNameNodeProcessReportThread;
import org.exp.dtfs.ni.report.thread.hdfs.HDFSNameNodeRPCClientConnectionNumberReportThread;
import org.exp.dtfs.ni.report.thread.hdfs.HDFSNameNodeRoleReportThread;
import org.exp.dtfs.ni.report.thread.hdfs.HDFSNameNodeSafeModeReportThread;
import org.exp.dtfs.ni.report.thread.hdfs.HDFSTotalCorruptBlockNumberReportThread;
import org.exp.dtfs.ni.report.thread.hdfs.HDFSTotalFileNumberReportThread;

public class MetricReporter implements Reporter {
    private static final Log LOG = LogFactory.getLog(MetricReporter.class);

    private List<ReportEntity> entities = new Vector<>();

    @Override
    public void register(ReportEntity entity) {
        entities.add(entity);
    }

    @Override
    public void remove(ReportEntity entity) {
        entities.remove(entity);
    }

    @Override
    public void start() {
        ScheduledExecutorService manager = Executors.newScheduledThreadPool(entities.size());
        entities.parallelStream().forEach(new Consumer<ReportEntity>() {
            @Override
            public void accept(ReportEntity t) {
                manager.scheduleAtFixedRate(t.getThread(), 0, t.getPeriod(), t.getTimeUnit());
            }
        });
    }

    public static void main(String[] args) {
        MetricReporter reporter = new MetricReporter();
        TimeUnit minUnit = TimeUnit.MINUTES;
        /*
         * @Type 基础运行类
         *
         * @Name 数据管理节点服务进程是否存在
         *
         * @Comment 检查进程，如果进程不在，则产生告警
         *
         * @Period 1分钟
         */
        reporter.register(new ReportEntity(new HDFSNameNodeProcessReportThread(), 1, minUnit));

        /*
         * @Type 基础运行类
         *
         * @Name NameNode连接状态是否正常
         *
         * @Comment 检查NameNode是否正常，是否可以连接
         *
         * @Period 1分钟
         */
        reporter.register(new ReportEntity(new HDFSNameNodeProcessReportThread(), 1, minUnit));

        /*
         * @Type 基础运行类
         *
         * @Name NameNode是否启用安全模式
         *
         * @Comment 检查NameNode是否处于安全模式，启用安全模式说明不能写入
         *
         * @Period 1分钟
         */
        reporter.register(new ReportEntity(new HDFSNameNodeSafeModeReportThread(), 1, minUnit));

        /*
         * @Type 基础运行类
         *
         * @Name NameNode主备角色
         *
         * @Comment 检查NameNode主备状态(master：主，slave：备)
         *
         * @Period 1分钟
         */
        reporter.register(new ReportEntity(new HDFSNameNodeRoleReportThread(), 1, minUnit));

        /*
         * @Type 基础运行类
         *
         * @Name 集群总文件数（FilesTotal）
         *
         * @Comment 采集集群中文件及文件夹的总个数
         *
         * @Period 1分钟
         */
        reporter.register(new ReportEntity(new HDFSTotalFileNumberReportThread(), 1, minUnit));

        /*
         * @Type 基础运行类
         *
         * @Name 元数据节点服务进程是否存在
         *
         * @Comment 检查进程，如果进程不在，则产生告警
         *
         * @Period 1分钟
         */
        reporter.register(new ReportEntity(new HBaseRegionServerProcessReportThread(), 1, minUnit));

        /*
         * @Type 基础运行类
         *
         * @Name 数据节点服务进程是否存在
         *
         * @Comment 检查进程，如果进程不在，则产生告警
         *
         * @Period 1分钟
         */
        reporter.register(new ReportEntity(new HDFSDataNodeProcessReportThread(), 1, minUnit));

        /*
         * @Type 基础运行类
         *
         * @Name NameNode客户端连接数
         *
         * @Comment 获取NameNode当前的连接数
         *
         * @Period 1分钟
         */
        reporter.register(new ReportEntity(new HDFSNameNodeRPCClientConnectionNumberReportThread(), 1, minUnit));

        /*
         * @Type 基础运行类
         *
         * @Name 小文件(≤2MB)数占比
         *
         * @Comment 获取小文件数占比（百分数）
         *
         * @Period 10分钟
         */
        reporter.register(new ReportEntity(new DTFSTinyFilePercentageReportThread(), 10, minUnit));

        /*
         * @Type 基础运行类
         *
         * @Name 集群中已损坏block总个数
         *
         * @Comment 采集集群中已损坏的block的总个数
         *
         * @Period 1分钟
         */
        reporter.register(new ReportEntity(new HDFSTotalCorruptBlockNumberReportThread(), 1, minUnit));

        /*
         * @Type 性能类
         *
         * @Name 单个数据节点的平均读取时间
         *
         * @Comment 采集单位时间内数据节点数据块的平均读取时间（单位：毫秒）
         *
         * @Period 1分钟
         */
        reporter.register(new ReportEntity(new HDFSDataNodeReadTimeReportThread(), 1, minUnit));

        /*
         * @Type 性能类
         *
         * @Name 单个数据节点的平均写入时间
         *
         * @Comment 采集单位时间内数据节点数据块的平均写入时间（单位：毫秒）
         *
         * @Period 1分钟
         */
        reporter.register(new ReportEntity(new HDFSDataNodeWriteTimeReportThread(), 1, minUnit));

        /*
         * @Type 基础运行类
         *
         * @Name 集群占用内存总数
         *
         * @Comment 获取集群占用的内存总数（单位：MB）
         *
         * @Period 5分钟
         */
        reporter.register(new ReportEntity(new HDFSNameNodeMemoryUsageReportThread(), 5, minUnit));

        /*
         * @Type 基础运行类
         *
         * @Name 存活元数据节点的个数
         *
         * @Comment 获取存活的元数据节点的个数
         *
         * @Period 1分钟
         */
        reporter.register(new ReportEntity(new HBaseRegionServerAliveNumberReportThread(), 1, minUnit));

        /*
         * @Type 基础运行类
         *
         * @Name 异常元数据节点的个数
         *
         * @Comment 获取异常的元数据节点的个数
         *
         * @Period 1分钟
         */
        reporter.register(new ReportEntity(new HBaseRegionServerAbnormalNumberReportThread(), 1, minUnit));

        /*
         * @Type 基础运行类
         *
         * @Name 集群磁盘空间占用率
         *
         * @Comment 获取集群存储总占用的容量（单位：GB）
         *
         * @Period 5分钟
         */
        reporter.register(new ReportEntity(new HDFSNameNodeCapacityUsageReportThread(), 5, minUnit));

        /*
         * @Type 基础运行类
         *
         * @Name 集群CPU占用率
         *
         * @Comment 获取集群CPU占用率（百分数）
         *
         * @Period 5分钟
         */
        reporter.register(new ReportEntity(new HDFSNameNodeCPUUsageReportThread(), 5, minUnit));

        LOG.info("Start metrics reporter.");
        reporter.start();
    }

}
