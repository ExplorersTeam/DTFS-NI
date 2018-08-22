package org.exp.dtfs.ni.report;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.HDFSConfigs;
import org.exp.dtfs.ni.entity.MetricMessage;
import org.exp.dtfs.ni.entity.MetricType;
import org.exp.dtfs.ni.utils.HDFSUtils;
import org.exp.dtfs.ni.utils.HTTPUtils;
import org.exp.dtfs.ni.utils.JSONUtils;
import org.exp.dtfs.ni.utils.KafkaUtils;

public class MetricReporter {
    private static final Log LOG = LogFactory.getLog(MetricReporter.class);

    private ScheduledExecutorService manager = Executors.newScheduledThreadPool(17);

    /*
     * @Type 基础运行类
     *
     * @Name 数据管理节点服务进程是否存在
     *
     * @Comment 检查进程，如果进程不在，则产生告警
     *
     * @Period 1分钟
     */
    private Runnable nn = new Runnable() {
        @Override
        public void run() {
            HDFSNameNodeProcessReport();
        }
    };

    /*
     * @Type 基础运行类
     *
     * @Name NameNode连接状态是否正常
     *
     * @Comment 检查NameNode是否正常，是否可以连接
     *
     * @Period 1分钟
     */
    private Runnable nnConn = new Runnable() {
        @Override
        public void run() {
            HDFSNameNodeProcessReport();
        }
    };

    private static void HDFSNameNodeProcessReport() {
        String nn1 = HDFSConfigs.getNameNode1HTTPAddr();
        String nn2 = HDFSConfigs.getNameNode2HTTPAddr();
        LOG.info("HDFS NameNodes are [" + nn1 + "] and [" + nn2 + "].");
        String nn1Hostname = nn1.split(Constants.COLON_DELIMITER)[0];
        String nn2Hostname = nn2.split(Constants.COLON_DELIMITER)[0];
        int nn1Port = Integer.parseInt(nn1.split(Constants.COLON_DELIMITER)[1]);
        int nn2Port = Integer.parseInt(nn2.split(Constants.COLON_DELIMITER)[1]);

        MetricMessage nn1Msg = new MetricMessage();
        MetricMessage nn2Msg = new MetricMessage();

        try {
            nn1Msg.setCompKey("Test CompKey");
            nn1Msg.setHostIP(InetAddress.getByName(nn1Hostname).getHostAddress());
            nn1Msg.setMetricCode("Test Code");
            nn1Msg.setMetricType(MetricType.STATUS);
            nn1Msg.setMetricValue(Boolean.toString(HDFSUtils.checkNameNodeAlive(HTTPUtils.buildURI(nn1Hostname, nn1Port))));

            nn2Msg.setCompKey("Test CompKey");
            nn2Msg.setHostIP(InetAddress.getByName(nn2Hostname).getHostAddress());
            nn2Msg.setMetricCode("Test Code");
            nn2Msg.setMetricType(MetricType.STATUS);
            nn2Msg.setMetricValue(Boolean.toString(HDFSUtils.checkNameNodeAlive(HTTPUtils.buildURI(nn2Hostname, nn2Port))));
        } catch (IOException | URISyntaxException e) {
            LOG.error(e.getMessage(), e);
        }

        try {
            KafkaUtils.produce(JSONUtils.buildJSONString(nn1Msg));
            KafkaUtils.produce(JSONUtils.buildJSONString(nn2Msg));
        } catch (InterruptedException | ExecutionException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public MetricReporter() {
        manager.scheduleAtFixedRate(nn, 0, 1, TimeUnit.MINUTES);
        manager.scheduleAtFixedRate(nnConn, 0, 1, TimeUnit.MINUTES);
    }

    public static void main(String[] args) {
        new MetricReporter();
    }

}
