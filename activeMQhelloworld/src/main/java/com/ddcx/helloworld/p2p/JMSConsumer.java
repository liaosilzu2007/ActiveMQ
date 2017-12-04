package com.ddcx.helloworld.p2p;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by liaosi on 2017/6/11.
 */
public class JMSConsumer {

    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER; //默认的连接用户名：null
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; //默认的连接密码
    private static final String BROKEN_URL = ActiveMQConnection.DEFAULT_BROKER_URL; //默认的连接地址


    public static void main(String[] args) {
        ConnectionFactory connectionFactory;    //连接工厂，用于常见连接
        Connection connection = null;  //连接
        Session session;    //会话。接受或者发送消息的线程
        Destination destination;    //消息的目的地
        MessageConsumer messageConsumer;    //消息消费者

        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEN_URL);
        try {
            connection = connectionFactory.createConnection();//通过连接工厂获取连接
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  //消费不需要加事务
            //创建一个名称为HelloWorld的消息队列，这个必须和生产者创建的队列对应
            destination = session.createQueue("helloworld! ActiveMQ");
            messageConsumer = session.createConsumer(destination);//创建的消息消费者

            while (true) {
                TextMessage textMessage = (TextMessage) messageConsumer.receive(100000);//每100秒接受一次
                if (textMessage != null) {
                    System.out.println("收到的消息：" + textMessage.getText());
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
