package com.ddcx.activespring.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by liaosi on 2017/6/19.
 */
@Component(value = "topicSender")
public class TopicSender {

    @Autowired
    @Qualifier(value = "jmsTopicTemplate")
    private JmsTemplate jmsTemplate;


    /**
     * 发送一条消息到指定的主题（目标）
     * @param topicName 主题名称
     * @param message   消息内容
     */
    public void send(String topicName, String message) {
        jmsTemplate.send(topicName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }

}
