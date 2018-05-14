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


    @Test
    public void comsumeMsg() {

        Properties props = new Properties();
        props.put("bootstrap.servers", Config.KAFKA_SERVER_ADRESS + ":" + Config.KAFKA_SERVER_PORT);
        props.put("group.id", Config.GROUP_ID);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(Config.TOPIC));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
            sleep(1);
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
