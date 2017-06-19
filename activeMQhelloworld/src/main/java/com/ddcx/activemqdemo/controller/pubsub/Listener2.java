package com.ddcx.activemqdemo.controller.pubsub;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 订阅者二的消息监听器
 * Created by liaosi on 2017/6/11.
 */
public class Listener2 implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("订阅者二收到的消息" + ((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
