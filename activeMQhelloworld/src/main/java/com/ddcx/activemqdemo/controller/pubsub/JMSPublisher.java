package com.ddcx.activemqdemo.controller.pubsub;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息发布者
 * Created by liaosi on 2017/6/11.
 */
public class JMSPublisher {

    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER; //默认的连接用户名：null
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; //默认的连接密码
    private static final String BROKEN_URL = ActiveMQConnection.DEFAULT_BROKER_URL; //默认的连接地址

    private static final int SEND_NUM = 10; //发送的消息数量


    public static void main(String[] args) {

        ConnectionFactory connectionFactory;    //连接工厂，用于常见连接
        Connection connection = null;  //连接
        Session session;    //会话。接受或者发送消息的线程
        Destination destination;    //消息的目的地
        MessageProducer messageProducer;    //消息生产者

        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEN_URL);
        try {
            connection = connectionFactory.createConnection();//通过连接工厂获取连接
            connection.start();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic("FirstTopic");  //创建一个名称为HelloWorld的消息主题
            messageProducer = session.createProducer(destination);  //创建消息生产者
            sendMessage(session, messageProducer);  //发送消息
            //因为这里加了事务，所以需要commit后才会真正发送
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 发送消息
     * @param session
     * @param messageProducer
     * @throws Exception
     */
    public static void sendMessage(Session session, MessageProducer messageProducer) throws Exception {

        for (int i = 5; i < SEND_NUM; i++) {
            //创建一条文本消息
            TextMessage textMessage = session.createTextMessage("ActiveMQ 发布消息" + i);
            System.out.println("发布消息：ActiveMQ 发布的消息" + i);
            //通过消息生产者发出消息
            messageProducer.send(textMessage);

        }
    }
}

