package org.exp.dtfs.ni.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.RetriableException;
import org.exp.dtfs.ni.conf.KafkaConfigs;
import org.exp.dtfs.ni.context.KafkaContext;

/**
 *
 * Operations to Kafka.
 *
 * @author ZhangQingliang
 *
 */
public class KafkaUtils {
    private static final Log LOG = LogFactory.getLog(KafkaUtils.class);

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
            producer.send(records, (metadata, exception) -> {
                if (null == exception) {
                    LOG.info("Message sent successfully, offset is [" + metadata.offset() + "].");
                } else {
                    if (exception instanceof RetriableException) {
                        LOG.error("Retriable exception occurred.", exception);
                    } else {
                        LOG.error("Exception occurred.", exception);
                    }
                }
            }).get();
        }
    }

    /**
     * Consume message from Kafka queue.
     * 
     * @param topics
     * @param timeout
     * @param function
     * @throws InterruptedException
     */
    public static void consume(Collection<String> topics, java.util.function.Consumer<ConsumerRecord<String, String>> function) throws InterruptedException {
        consume(topics, 0, function);
    }

    /**
     * Consume message from Kafka queue.
     * 
     * @param topics
     * @param period
     * @param function
     * @throws InterruptedException
     */
    public static void consume(Collection<String> topics, long period, java.util.function.Consumer<ConsumerRecord<String, String>> function)
            throws InterruptedException {
        try (Consumer<String, String> consumer = new KafkaConsumer<>(KafkaContext.getConsumerProps())) {
            consumer.subscribe(topics);
            while (true) {
                // InterruptedException: ignore.
                ConsumerRecords<String, String> records = consumer.poll(KafkaConfigs.getConsumerTimeout());
                records.forEach(function);
                if (0 < period) {
                    Thread.sleep(period);
                }
            }
        }
    }

    // For test.
    public static void main(String[] args) {
        try {
            consume(Arrays.asList(args), record -> LOG.info(record.value()));
        } catch (InterruptedException e) {
            LOG.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

}
