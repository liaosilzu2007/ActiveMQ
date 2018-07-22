package com.lzumetal.mq.kafka.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Before;
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

    private KafkaConsumer<String, String> consumer;

    @Before
    public void init() {
        Properties props = new Properties();
        props.put("bootstrap.servers", Constants.KAFKA_SERVER_ADRESS + ":" + Constants.KAFKA_SERVER_PORT);
        props.put("group.id", Constants.GROUP_ID);
        props.put("enable.auto.commit", "true"); //消费之后自动提交
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
    }



    @Test
    public void comsumeMsg() {


        consumer.subscribe(Arrays.asList(Constants.MY_TOPIC));
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
