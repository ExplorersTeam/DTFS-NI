package org.exp.dtfs.ni.utils;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.exp.dtfs.ni.common.KafkaConfigs;
import org.exp.dtfs.ni.common.KafkaContext;

public class KafkaUtils {

    private KafkaUtils() {
        // Do nothing.
    }

    public static void producerUtil(String message) throws InterruptedException, ExecutionException {
        Properties props = KafkaContext.getProps();
        String topicName = KafkaConfigs.getKafkaTopicName();
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        ProducerRecord<String, String> records = new ProducerRecord<String, String>(topicName, message);
        producer.send(records).get();
        producer.close();
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        producerUtil("zzzzzzzzz");
        producerUtil("fffffffff");
        producerUtil("wwwwwwwww");
    }
}
