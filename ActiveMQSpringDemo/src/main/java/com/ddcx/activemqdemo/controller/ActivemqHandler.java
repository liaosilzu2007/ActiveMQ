package com.ddcx.activemqdemo.controller;

import com.ddcx.activemqdemo.mq.producer.queue.QueueSender;
import com.ddcx.activemqdemo.mq.producer.topic.TopicSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by liaosi on 2017/6/19.
 */
@Controller
@RequestMapping(value = "/activemq")
public class ActivemqHandler {

    @Resource
    private QueueSender queueSender;
    @Resource
    private TopicSender topicSender;

    /**
     * 发送消息到队列
     * Queue队列：仅有一个订阅者会收到消息，消息一旦被处理就不会存在队列中
     * @param message
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sendToQueue")
    public String sendToQueue(String message) {
        String result;
        try {
            queueSender.send("testQueue", message);
            result = "success";
        } catch (Exception e) {
            result = e.getCause().getMessage();
        }
        return result;
    }

    /**
     * 发送消息到主题
     * Topic主题 ：放入一个消息，所有订阅者都会收到
     * 这个是主题目的地是一对多的
     * @param message
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "/sendToTopic")
    public String sendToTopic(String message) {
        String result;
        try {
            topicSender.send("testTopic", message);
            result = "success";
        } catch (Exception e) {
            result = e.getCause().getMessage();
        }
        return result;
    }

}
