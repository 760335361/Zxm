package com.atguigu.zxm.manager.mqTest;
import jakarta.annotation.PostConstruct;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class MsgSender {
    // 保留原生生产者对象
    private DefaultMQProducer producer;

    // ========== 从YAML读取配置（完全替代硬编码） ==========
    @Value("${rocketmq.name-server}")
    private String namesrvAddr;//端口

    @Value("${rocketmq.producer.group}")
    private String producerGroup;//生产者分组

    @Value("${rocketmq.topic}")
    private String topic;//产者/消费者统一配置，保证一致性

    // 项目启动自动初始化（替换原构造方法，更符合Spring规范）
    @PostConstruct
    public void initProducer() {
        try {
            // 1. 创建生产者，读取YAML中的分组名
            producer = new DefaultMQProducer(producerGroup);
            // 2. 配置Namesrv地址，读取YAML中的地址
            producer.setNamesrvAddr(namesrvAddr);
            // 3. 启动生产者
            producer.start();
            System.out.println("✅ 【消息发送器】初始化成功！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ 【消息发送器】初始化失败！");
        }
    }

    // 保留你原有的发送方法，仅替换硬编码的topic
    public void send(String content) {
        try {
            // 读取YAML中的主题名，消息发送逻辑完全不变
            Message msg = new Message(topic, content.getBytes());
            SendResult result = producer.send(msg);
            System.out.println("✅ 消息发送成功 → " + result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ 消息发送失败 → " + content);
        }
    }
}
