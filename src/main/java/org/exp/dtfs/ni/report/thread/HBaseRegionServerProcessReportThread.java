package org.exp.dtfs.ni.report.thread;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.HBaseConfigs;
import org.exp.dtfs.ni.conf.KafkaConfigs;
import org.exp.dtfs.ni.entity.MetricMessage;
import org.exp.dtfs.ni.entity.MetricType;
import org.exp.dtfs.ni.utils.HBaseUtils;
import org.exp.dtfs.ni.utils.HDFSUtils;
import org.exp.dtfs.ni.utils.JSONUtils;
import org.exp.dtfs.ni.utils.KafkaUtils;

public class HBaseRegionServerProcessReportThread extends HBaseReportThread {

    @Override
    public void work() {
        try {
            List<String> rss = HBaseUtils.listRegionServerHostnames();
            rss.parallelStream().forEach(new Consumer<String>() {
                @Override
                public void accept(String t) {
                    try {
                        processReport(t);
                    } catch (NumberFormatException | IOException | URISyntaxException | InterruptedException | ExecutionException e) {
                        LOG.error(e.getMessage(), e);
                    }
                }
            });
        } catch (URISyntaxException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private static void processReport(String hostname) throws NumberFormatException, IOException, URISyntaxException, InterruptedException, ExecutionException {
        String ip = InetAddress.getByName(hostname).getHostAddress();

        MetricMessage message = new MetricMessage();
        message.setCompKey(ip + Constants.TRANSFER_VERTICAL_DELIMITER + HBASE_SERVER_KEY + HBaseConfigs.getRegionServerPort());
        message.setHostIP(ip);
        message.setMetricCode(HBASE_STATUS_CODE);
        message.setMetricType(MetricType.STATUS);
        message.setMetricValue(Boolean.toString(HDFSUtils.checkNameNode1Alive()));

        String msgStr = JSONUtils.buildJSONString(message);
        LOG.info("Send message [" + msgStr + "] into Kafka queue.");
        KafkaUtils.produce(KafkaConfigs.getKafkaMetricTopicName(), msgStr);
    }

}
