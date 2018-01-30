package com.lzumetal.mq.kafka.demo;


import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: </p>
 * Producer由一个持有未发送消息记录的资源池和一个用来向Kafka集群发送消息记录的后台IO线程组成。
 * 使用后未关闭producer将导致这些资源泄露。
 * @author: liaosi
 * @date: 2018-01-30
 */
public class KafkaProducer {

    private final Producer<Integer, String> producer;
    private final String topic;
    private final Properties properties;

    public KafkaProducer(String topic) {
        properties = new Properties();

        //key.serializer 和 value.serializer 指定使用什么序列化方式将用户提供的key和value进行序列化。
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("metadata.broker.list", "119.23.246.206:9092");

        //当 retries > 0 时，如果发送失败，会自动尝试重新发送数据。发送次数为retries设置的值。
        properties.put("retries", 3);
        //ack 配置项用来控制producer要求leader确认多少消息后返回调用成功。当值为0时producer不需要等待任何确认消息。当值为1时只需要等待leader确认。当值为-1或all时需要全部ISR集合返回确认才可以返回成功。
        //properties.put("acks", "1");
        producer = new Producer<>(new ProducerConfig(properties));
        this.topic = topic;
    }

    public static void main(String[] args) {
        new KafkaProducer(Config.topic).produceMsg();
    }


    public void produceMsg() {
        for (int i = 0; i < 10; i++) {
            String msg = "Message_test_" + i;
            System.out.println("produce : " + msg);
            //send方法是异步的。当它被调用时，它会将消息记录添加到待发送缓冲区并立即返回。
            //使用这种方式可以使生产者聚集一批消息记录后一起发送，从而提高效率。
            producer.send(new KeyedMessage<Integer, String>(topic, msg));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        producer.close();
    }
}
