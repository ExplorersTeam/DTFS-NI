package org.exp.dtfs.ni.report.thread;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.HDFSConfigs;
import org.exp.dtfs.ni.conf.KafkaConfigs;
import org.exp.dtfs.ni.entity.MetricMessage;
import org.exp.dtfs.ni.entity.MetricType;
import org.exp.dtfs.ni.utils.HDFSUtils;
import org.exp.dtfs.ni.utils.JSONUtils;
import org.exp.dtfs.ni.utils.KafkaUtils;

public class HDFSNameNodeRPCClientConnectionNumberReportThread extends HDFSReportThread {

    @Override
    public void work() {
        try {
            String activeNNHostname = HDFSUtils.getActiveNameNodeHostname();
            String activeNNIP = InetAddress.getByName(activeNNHostname).getHostAddress();
            MetricMessage message = new MetricMessage();
            message.setCompKey(activeNNIP + Constants.TRANSFER_VERTICAL_DELIMITER + HDFS_SERVER_KEY + portStr(activeNNHostname));
            message.setHostIP(activeNNIP);
            message.setMetricCode(HDFS_STATUS_CODE);
            message.setMetricType(MetricType.STATUS);
            message.setMetricValue(Integer.toString(HDFSUtils.rpcClientConnNum()));

            String messageStr = JSONUtils.buildJSONString(message);
            LOG.info("Send message [" + messageStr + "] into Kafka queue.");
            KafkaUtils.produce(KafkaConfigs.getKafkaMetricTopicName(), messageStr);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private static String portStr(String hostname) throws UnknownHostException {
        String[] nn1Addrs = HDFSConfigs.getNameNode1RPCAddr().split(Constants.COLON_DELIMITER);
        String[] nn2Addrs = HDFSConfigs.getNameNode2RPCAddr().split(Constants.COLON_DELIMITER);
        String port = "8020";
        if (hostname.equals(nn1Addrs[0])) {
            port = nn1Addrs[1];
        } else if (hostname.equals(nn2Addrs[0])) {
            port = nn2Addrs[1];
        }
        return port;
    }

}
