package com.ddcx.activemqdemo.mq.comsumer.queue;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by liaosi on 2017/6/19.
 */
@Component
public class QueueListener1 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("QueueListener1接收到的消息：" + ((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
