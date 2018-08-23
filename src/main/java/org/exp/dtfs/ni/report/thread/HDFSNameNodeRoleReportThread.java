package org.exp.dtfs.ni.report.thread;

import java.net.InetAddress;

import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.HDFSConfigs;
import org.exp.dtfs.ni.conf.KafkaConfigs;
import org.exp.dtfs.ni.entity.MetricMessage;
import org.exp.dtfs.ni.entity.MetricType;
import org.exp.dtfs.ni.utils.HDFSUtils;
import org.exp.dtfs.ni.utils.JSONUtils;
import org.exp.dtfs.ni.utils.KafkaUtils;

public class HDFSNameNodeRoleReportThread extends HDFSReportThread {

    @Override
    public void work() {
        try {
            nn1RoleReport();
            nn2RoleReport();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private static void nn1RoleReport() throws Exception {
        String[] nn1Addrs = HDFSConfigs.getNameNode1HTTPAddr().split(Constants.COLON_DELIMITER);
        String nn1IP = InetAddress.getByName(nn1Addrs[0]).getHostAddress();
        MetricMessage nn1Msg = new MetricMessage();
        nn1Msg.setCompKey(nn1IP + Constants.TRANSFER_VERTICAL_DELIMITER + HDFS_SERVER_KEY + nn1Addrs[1]);
        nn1Msg.setHostIP(nn1IP);
        nn1Msg.setMetricCode(HDFS_STATUS_CODE);
        nn1Msg.setMetricType(MetricType.STATUS);
        nn1Msg.setMetricValue(Boolean.toString(HDFSUtils.activeNameNodeHostname().equals(nn1Addrs[0])));

        String nn1MsgStr = JSONUtils.buildJSONString(nn1Msg);
        LOG.info("Send message [" + nn1MsgStr + "] into Kafka queue.");
        KafkaUtils.produce(KafkaConfigs.getKafkaMetricTopicName(), nn1MsgStr);
    }

    private static void nn2RoleReport() throws Exception {
        String[] nn2Addrs = HDFSConfigs.getNameNode2HTTPAddr().split(Constants.COLON_DELIMITER);
        String nn2IP = InetAddress.getByName(nn2Addrs[0]).getHostAddress();
        MetricMessage nn2Msg = new MetricMessage();
        nn2Msg.setCompKey(nn2IP + Constants.TRANSFER_VERTICAL_DELIMITER + HDFS_SERVER_KEY + nn2Addrs[1]);
        nn2Msg.setHostIP(nn2IP);
        nn2Msg.setMetricCode(HDFS_STATUS_CODE);
        nn2Msg.setMetricType(MetricType.STATUS);
        nn2Msg.setMetricValue(Boolean.toString(HDFSUtils.activeNameNodeHostname().equals(nn2Addrs[0])));

        String nn2MsgStr = JSONUtils.buildJSONString(nn2Msg);
        LOG.info("Send message [" + nn2MsgStr + "] into Kafka queue.");
        KafkaUtils.produce(KafkaConfigs.getKafkaMetricTopicName(), nn2MsgStr);
    }

}
