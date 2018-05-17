package com.lzumetal.mq.kafka.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: </p>
 *
 * @author: liaosi
 * @date: 2018-01-30
 */
public class MyKafkaConsumer {


    /**
     * 自动提交offset
     */
    @Test
    public void comsumeMsgAutoCommit() {

        Properties props = new Properties();
        props.put("bootstrap.servers", Constants.KAFKA_SERVER_ADRESS + ":" + Constants.KAFKA_SERVER_PORT);
        props.put("group.id", Constants.GROUP_ID);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(Constants.TOPIC));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
            sleep(1);
        }
    }

    /**
     * 手动提交offset
     */
    @Test
    public void consumerMsgManualCommit() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("foo", "bar"));
        final int minBatchSize = 200;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
            }
            if (buffer.size() >= minBatchSize) {
                insertIntoDb(buffer);
                consumer.commitSync();
                buffer.clear();
            }
        }
    }

    private void insertIntoDb(List<ConsumerRecord<String, String>> buffer) {
        for (ConsumerRecord<String, String> record : buffer) {
            System.out.printf("insertIntoDb:offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        }
    }


    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
