package com.ddcx.activemq.pubsub;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 订阅者一的消息监听器
 * Created by liaosi on 2017/6/11.
 */
public class Listener1 implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("订阅者一收到的消息" + ((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
