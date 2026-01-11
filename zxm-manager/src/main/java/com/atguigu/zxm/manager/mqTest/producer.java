package com.atguigu.zxm.manager.mqTest;

import lombok.val;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class producer {
    public static void main(String[] args) throws Exception {
    //先建立producer
        DefaultMQProducer producer = new DefaultMQProducer("producer_group_zxm");
    //发送给NamesrvAddr
        producer.setNamesrvAddr("localhost:9876");
    //启动producer
        producer.start();

    //发送给topic
        String mag = "朱学敏最帅了";
        Message message = new Message("topic", "tags", mag.getBytes());
        producer.send(message);
    //关闭
        producer.shutdown();
    }
}
