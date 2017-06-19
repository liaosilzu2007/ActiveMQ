package com.ddcx.activemqdemo.controller.p2p;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by liaosi on 2017/6/11.
 */
public class JMSProducer {

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
            /*
            第一个参数true：表示该对话是要加事务的。
            第二个参数表示消息确认的方式：
                Session.AUTO_ACKNOWLEDGE（最常用的一种）。 当客户成功的从receive方法返回的时候， 或者从MessageListener.onMessage方法成功返回的时候，会话自动确认客户收到的消息。
                Session.CLIENT_ACKNOWLEDGE。 客户通过消息的 acknowledge 方法确认消息。
                    需要注意的是，在这种模式中，确认是在会话层上进行：确认一个被消费的消息将自动确认所有已被会话消 费的消息。
                    例如，如果一个消息消费者消费了 10 个消息，然后确认第 5 个消息，那么所有 10 个消息都被确认。
                Session.DUPS_ACKNOWLEDGE。 该选择只是会话迟钝第确认消息的提交。
                    如果 JMS provider 失败，那么可能会导致一些重复的消息。如果是重复的消息，那么 JMS provider 必须把消息头的 JMSRedelivered 字段设置为 true。
                注意：这三种方式只是针对 直接 Receive 方式 而言，但以后我们一般是使用 Listener 监听方式
             */
            destination = session.createQueue("helloworld! ActiveMQ");  //创建一个名称为HelloWorld的消息队列
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
            TextMessage textMessage = session.createTextMessage("ActiveMQ 发送消息" + i);
            System.out.println("发送消息：ActiveMQ 发送消息" + i);
            //通过消息生产者发出消息
            messageProducer.send(textMessage);

        }
    }
}

