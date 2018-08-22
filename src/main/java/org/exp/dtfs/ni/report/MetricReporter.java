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
import org.apache.http.HttpStatus;
import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.HDFSConfigs;
import org.exp.dtfs.ni.entity.MetricMessage;
import org.exp.dtfs.ni.entity.MetricType;
import org.exp.dtfs.ni.utils.HTTPUtils;
import org.exp.dtfs.ni.utils.JSONUtils;
import org.exp.dtfs.ni.utils.KafkaUtils;

public class MetricReporter {
    private static final Log LOG = LogFactory.getLog(MetricReporter.class);

    private ScheduledExecutorService manager = Executors.newScheduledThreadPool(17);

    /*
     * TODO Changed them.
     */
    private static final String TEST_COMP_KEY = "Test CompKey";
    private static final String TEST_METRIC_CODE = "Test Code";

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
        try {
            String[] nn1Addrs = HDFSConfigs.getNameNode1HTTPAddr().split(Constants.COLON_DELIMITER);

            MetricMessage nn1Msg = new MetricMessage();
            nn1Msg.setCompKey(TEST_COMP_KEY);
            nn1Msg.setHostIP(InetAddress.getByName(nn1Addrs[0]).getHostAddress());
            nn1Msg.setMetricCode(TEST_METRIC_CODE);
            nn1Msg.setMetricType(MetricType.STATUS);
            nn1Msg.setMetricValue(Boolean.toString(HttpStatus.SC_OK == HTTPUtils.sendGETRequest(nn1Addrs[0], Integer.parseInt(nn1Addrs[1]))));

            String nn1MsgStr = JSONUtils.buildJSONString(nn1Msg);
            LOG.info("Send message [" + nn1MsgStr + "] into Kafka queue.");
            KafkaUtils.produce(nn1MsgStr);
        } catch (IOException | URISyntaxException | InterruptedException | ExecutionException e) {
            LOG.error(e.getMessage(), e);
        }

        try {
            String[] nn2Addrs = HDFSConfigs.getNameNode2HTTPAddr().split(Constants.COLON_DELIMITER);

            MetricMessage nn2Msg = new MetricMessage();
            nn2Msg.setCompKey(TEST_COMP_KEY);
            nn2Msg.setHostIP(InetAddress.getByName(nn2Addrs[0]).getHostAddress());
            nn2Msg.setMetricCode(TEST_METRIC_CODE);
            nn2Msg.setMetricType(MetricType.STATUS);
            nn2Msg.setMetricValue(Boolean.toString(HttpStatus.SC_OK == HTTPUtils.sendGETRequest(nn2Addrs[0], Integer.parseInt(nn2Addrs[1]))));

            String nn2MsgStr = JSONUtils.buildJSONString(nn2Msg);
            LOG.info("Send message [" + nn2MsgStr + "] into Kafka queue.");
            KafkaUtils.produce(nn2MsgStr);
        } catch (InterruptedException | ExecutionException | IOException | NumberFormatException | URISyntaxException e) {
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
