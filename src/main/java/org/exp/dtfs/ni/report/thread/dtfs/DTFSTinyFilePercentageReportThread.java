package org.exp.dtfs.ni.report.thread.dtfs;

import java.net.InetAddress;

import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.KafkaConfigs;
import org.exp.dtfs.ni.entity.MetricMessage;
import org.exp.dtfs.ni.entity.MetricType;
import org.exp.dtfs.ni.utils.DTFSUtils;
import org.exp.dtfs.ni.utils.HDFSUtils;
import org.exp.dtfs.ni.utils.JSONUtils;
import org.exp.dtfs.ni.utils.KafkaUtils;

public class DTFSTinyFilePercentageReportThread extends DTFSReportThread {

    @Override
    public void work() {
        try {
            String activeNNHostname = HDFSUtils.getActiveNameNodeHostname();
            String activeNNIP = InetAddress.getByName(activeNNHostname).getHostAddress();
            MetricMessage message = new MetricMessage();
            message.setCompKey(activeNNIP + Constants.TRANSFER_VERTICAL_DELIMITER + DTFS_SERVER_KEY + HDFSUtils.getNameNodeHTTPPort(activeNNHostname));
            message.setHostIP(activeNNIP);
            message.setMetricCode(DTFS_STATUS_CODE);
            message.setMetricType(MetricType.STATUS);
            message.setMetricValue(Float.toString(DTFSUtils.getTinyFilePercentage()));

            String messageStr = JSONUtils.buildJSONString(message);
            LOG.info("Send message [" + messageStr + "] into Kafka queue.");
            KafkaUtils.produce(KafkaConfigs.getKafkaMetricTopicName(), messageStr);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

}
