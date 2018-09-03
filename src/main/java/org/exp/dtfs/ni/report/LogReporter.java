package org.exp.dtfs.ni.report;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exp.dtfs.ni.common.Constants;
import org.exp.dtfs.ni.conf.CommonConfigs;
import org.exp.dtfs.ni.conf.KafkaConfigs;
import org.exp.dtfs.ni.entity.LogMessage;
import org.exp.dtfs.ni.utils.JSONUtils;
import org.exp.dtfs.ni.utils.KafkaUtils;

public class LogReporter {
    private static final Log LOG = LogFactory.getLog(LogReporter.class);

    public void start(String ip, int port, String serverName) throws InterruptedException {
        KafkaUtils.consume(Arrays.asList(KafkaConfigs.getKafkaLogFlumeTmpTopicName()), CommonConfigs.getLogReportPeriod(), record -> {
            boolean warnReported = false;
            String log = record.value();
            String[] logStrs = log.split(" ");
            for (int i = 0; i < logStrs.length; ++i) {
                if (logStrs[i].equals("WARN") || logStrs[i].equals("ERROR") || logStrs[i].equals("FATAL")) {
                    produceMessage(ip, port, serverName, log);
                    if (!warnReported) {
                        warnReported = true;
                    }
                    break;
                }
            }
            if (!warnReported) {
                produceMessage(ip, port, serverName, "Service is in normal status.");
            }
        });
    }

    private static void produceMessage(String ip, int port, String serverName, String content) {
        LogMessage message = new LogMessage();
        message.setCompKey(ip + Constants.VERTICAL_DELIMITER + serverName + Constants.UNDERLINE_DELIMITER + port);
        message.setDueTime(0);
        message.setHostIP(ip);
        message.setLog(content);
        message.setStatus("OK");
        message.setSrcPath(CommonConfigs.getLogPath());
        try {
            String msgStr = JSONUtils.buildJSONString(message);
            LOG.info("Produce message into Kafka queue, content is [" + msgStr + "].");
            KafkaUtils.produce(KafkaConfigs.getKafkaLogTopicName(), msgStr);
        } catch (InterruptedException | ExecutionException | IOException e) {
            // InterruptedException: ignore.
            LOG.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        LogReporter reporter = new LogReporter();
        // Add arguments to execute command.
        // Arg 0 - Server IP.
        // Arg 1 - Server port.
        // Arg 2 - Server name.
        try {
            reporter.start(args[0], Integer.parseInt(args[1]), args[2]);
        } catch (NumberFormatException | InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }
    }

}
