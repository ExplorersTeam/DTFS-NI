package org.exp.dtfs.ni.report.thread.hdfs;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.HDFSConfigs;
import org.exp.dtfs.ni.conf.KafkaConfigs;
import org.exp.dtfs.ni.entity.MetricCode;
import org.exp.dtfs.ni.entity.MetricMessage;
import org.exp.dtfs.ni.entity.MetricType;
import org.exp.dtfs.ni.utils.HDFSUtils;
import org.exp.dtfs.ni.utils.JSONUtils;
import org.exp.dtfs.ni.utils.KafkaUtils;

public class HDFSDataNodeReadTimeReportThread extends HDFSReportThread {

    @Override
    public void work() {
        try {
            List<String> dns = HDFSUtils.listDataNodeHostnames();
            dns.parallelStream().forEach(t -> {
                try {
                    avgReadTimeReport(t);
                } catch (NumberFormatException | IOException | URISyntaxException | InterruptedException | ExecutionException e) {
                    LOG.error(e.getMessage(), e);
                }
            });
        } catch (URISyntaxException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private static void avgReadTimeReport(String hostname) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        String ip = InetAddress.getByName(hostname).getHostAddress();
        MetricMessage message = new MetricMessage();
        message.setCompKey(ip + Constants.VERTICAL_DELIMITER + HDFS_SERVER_KEY + HDFSConfigs.getDataNodeHTTPAddr().split(Constants.COLON)[1]
                + Constants.VERTICAL_DELIMITER + HDFS_DN_KEY);
        message.setHostIP(ip);
        message.setMetricCode(MetricCode.P_HB_DNREAD);
        message.setMetricType(MetricType.PERFORMANCE);
        message.setMetricValue(Float.toString(HDFSUtils.getDataNodeAvgReadTime(ip)));

        String msgStr = JSONUtils.buildJSONString(message);
        LOG.info("Send message [" + msgStr + "] into Kafka queue.");
        KafkaUtils.produce(KafkaConfigs.getKafkaMetricTopicName(), msgStr);
    }

}
