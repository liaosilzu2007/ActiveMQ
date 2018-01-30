package com.lzumetal.mq.kafka.demo;

/**
 * <p>Description: </p>
 *
 * @author: liaosi
 * @date: 2018-01-30
 */
public class Config {
    final static String zkConnect = "119.23.246.206:2181";
    final static String groupId = "group1";
    final static String topic = "topic1";
    final static String kafkaServerURL = "119.23.246.206";
    final static int kafkaServerPort = 9092;
    final static int kafkaProducerBufferSize = 64 * 1024;
    final static int connectionTimeOut = 20000;
    final static int reconnectInterval = 10000;
    final static String topic2 = "topic2";
    final static String topic3 = "topic3";
    final static String clientId = "SimpleConsumerDemoClient";

}
