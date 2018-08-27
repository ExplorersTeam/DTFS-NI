package org.exp.dtfs.ni.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.RetriableException;
import org.exp.dtfs.ni.context.KafkaContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Operations to Kafka.
 *
 * @author ZhangQingliang
 *
 */
public class KafkaUtils {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaUtils.class);

    private KafkaUtils() {
        // Do nothing.
    }

    /**
     * Produce message into Kafka queue.
     *
     * @param message
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static void produce(String topic, String message) throws InterruptedException, ExecutionException {
        try (Producer<String, String> producer = new KafkaProducer<>(KafkaContext.getProducerProps())) {
            // Record with no key.
            ProducerRecord<String, String> records = new ProducerRecord<>(topic, message);
            // producer.send(records).get();
            producer.send(records, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (null == exception) {
                        LOG.info("Message sent successfully, offset is [" + metadata.offset() + "].");
                    } else {
                        if (exception instanceof RetriableException) {
                            LOG.error("Retriable exception occurred.", exception);
                        } else {
                            LOG.error("Exception occurred.", exception);
                        }
                    }

                }
            }).get();
        }
        // producer.close();
    }

    /**
     * Consume message from Kafka queue.
     * 
     * @param topics
     * @param timeout
     * @param function
     */
    public static void consumer(Collection<String> topics, long timeout, java.util.function.Consumer<ConsumerRecord<String, String>> function) {
        try (Consumer<String, String> consumer = new KafkaConsumer<>(KafkaContext.getConsumerProps())) {
            consumer.subscribe(topics);
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(timeout);
                records.forEach(function);
            }
        }
    }

    // For test.
    public static void main(String[] args) {
        consumer(Arrays.asList(args), 100, record -> {
            LOG.info(record.value());
        });
    }

}
