package org.exp.dtfs.ni.report.thread.hbase;

import java.net.InetAddress;

import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.KafkaConfigs;
import org.exp.dtfs.ni.entity.MetricCode;
import org.exp.dtfs.ni.entity.MetricMessage;
import org.exp.dtfs.ni.entity.MetricType;
import org.exp.dtfs.ni.utils.HBaseUtils;
import org.exp.dtfs.ni.utils.JSONUtils;
import org.exp.dtfs.ni.utils.KafkaUtils;

public class HBaseRegionServerAbnormalNumberReportThread extends HBaseReportThread {

    @Override
    public void work() {
        try {
            String[] infos = HBaseUtils.listAliveRegionServerInfos().get(0).split(Constants.COMMA_DELIMITER);
            String ip = InetAddress.getByName(infos[0]).getCanonicalHostName();
            MetricMessage message = new MetricMessage();
            message.setCompKey(ip + Constants.VERTICAL_DELIMITER + HBASE_SERVER_KEY + infos[1]);
            message.setHostIP(ip);
            message.setMetricCode(MetricCode.P_HB_DNEXPNUM);
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
