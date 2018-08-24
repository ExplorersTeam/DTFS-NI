package org.exp.dtfs.ni.report.thread.hbase;

import org.exp.dtfs.ni.conf.KafkaConfigs;
import org.exp.dtfs.ni.entity.MetricMessage;
import org.exp.dtfs.ni.entity.MetricType;
import org.exp.dtfs.ni.utils.HBaseUtils;
import org.exp.dtfs.ni.utils.JSONUtils;
import org.exp.dtfs.ni.utils.KafkaUtils;

public class HBaseRegionServerAbnormalNumberReportThread extends HBaseReportThread {

    @Override
    public void work() {
        try {
            // TODO:wait to define IP.
            String ip = "";
            MetricMessage message = new MetricMessage();
            // TODO:wait to define CompKey.
            message.setCompKey("");
            message.setHostIP(ip);
            message.setMetricCode(HBASE_STATUS_CODE);
            message.setMetricType(MetricType.STATUS);
            message.setMetricValue(Integer.toString(HBaseUtils.getAbnormalRegionServerNumber()));
            String msgStr = JSONUtils.buildJSONString(message);
            LOG.info("Send message [" + msgStr + "] into Kafka queue.");
            KafkaUtils.produce(KafkaConfigs.getKafkaMetricTopicName(), msgStr);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
