package org.exp.dtfs.ni.utils;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.RetriableException;
import org.exp.dtfs.ni.common.KafkaConfigs;
import org.exp.dtfs.ni.common.KafkaContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaUtils {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaUtils.class);

    private KafkaUtils() {
        // Do nothing.
    }

    public static void producerUtil(String message) throws InterruptedException, ExecutionException {
        Properties props = KafkaContext.getProps();
        String topicName = KafkaConfigs.getKafkaTopicName();
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        ProducerRecord<String, String> records = new ProducerRecord<String, String>(topicName, message);
        // producer.send(records).get();
        producer.send(records, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (null == exception) {
                    LOG.info("***[message sent successfully!]***");
                } else {
                    if (exception instanceof RetriableException) {
                        LOG.error("Kafka_RetriableException---------->>" + exception);
                    } else {
                        LOG.error("Kafka_NoRetriableException---------->>" + exception);
                    }
                }

            }
        }).get();
        producer.close();
    }

    // TODO:To be deleted below code. Just for test.
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        producerUtil("zzzzzzzzz");
        producerUtil("fffffffff");
        producerUtil("wwwwwwwww");
    }
}
