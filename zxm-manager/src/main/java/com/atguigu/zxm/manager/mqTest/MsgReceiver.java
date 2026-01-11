package com.atguigu.zxm.manager.mqTest;
import jakarta.annotation.PostConstruct;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class MsgReceiver {

    // ========== 从YAML读取配置（完全替代硬编码） ==========
    @Value("${rocketmq.name-server}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.group}")
    private String consumerGroup;

    @Value("${rocketmq.topic}")
    private String topic;

    // 项目启动自动启动监听，逻辑与原版一致
    @PostConstruct
    public void startListen() {
        try {
            // 1. 创建接收器，读取YAML中的消费者组名
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
            // 2. 配置MQ地址，读取YAML中的地址
            consumer.setNamesrvAddr(namesrvAddr);
            // 3. 订阅消息主题，读取YAML中的主题名
            consumer.subscribe(topic, "*");
            // 4. 收到消息自动执行（消费逻辑完全保留原版）
            consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
                for (MessageExt m : msgs) {
                    String msgContent = new String(m.getBody());
                    System.out.println("📥 收到消息 → " + msgContent);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; // 标记消费成功
            });
            // 5. 启动接收器
            consumer.start();
            System.out.println("✅ 【消息接收器】初始化成功，等待接收消息！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ 【消息接收器】初始化失败！");
        }
    }
}
