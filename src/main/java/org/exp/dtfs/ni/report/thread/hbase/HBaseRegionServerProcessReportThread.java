package org.exp.dtfs.ni.report.thread.hbase;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.List;

import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.HBaseConfigs;
import org.exp.dtfs.ni.conf.KafkaConfigs;
import org.exp.dtfs.ni.entity.MetricCode;
import org.exp.dtfs.ni.entity.MetricMessage;
import org.exp.dtfs.ni.entity.MetricType;
import org.exp.dtfs.ni.utils.HBaseUtils;
import org.exp.dtfs.ni.utils.JSONUtils;
import org.exp.dtfs.ni.utils.KafkaUtils;

public class HBaseRegionServerProcessReportThread extends HBaseReportThread {

    @Override
    public void work() {
        try {
            List<String> rss = HBaseUtils.listRegionServerHostnames();
            rss.parallelStream().forEach(t -> {
                try {
                    processReport(t);
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            });
        } catch (URISyntaxException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private static void processReport(String hostname) throws Exception {
        String ip = InetAddress.getByName(hostname).getHostAddress();

        MetricMessage message = new MetricMessage();
        message.setCompKey(
                ip + Constants.VERTICAL_DELIMITER + HBASE_SERVER_KEY + HBaseConfigs.getRegionServerPort() + Constants.VERTICAL_DELIMITER + HBASE_RS_KEY);
        message.setHostIP(ip);
        message.setMetricCode(MetricCode.P_HB_REGPROC);
        message.setMetricType(MetricType.STATUS);
        message.setMetricValue(Boolean.toString(HBaseUtils.checkRegionServerAlive(ip)));

        String msgStr = JSONUtils.buildJSONString(message);
        LOG.info("Send message [" + msgStr + "] into Kafka queue.");
        KafkaUtils.produce(KafkaConfigs.getKafkaMetricTopicName(), msgStr);
    }

}
