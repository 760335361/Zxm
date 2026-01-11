package com.atguigu.zxm.manager.mqTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msg") // 接口路径，随便写
public class MsgTestController {
    // 注入消息发送器（Spring自动管理，无需手动创建）
    @Autowired
    private MsgSender msgSender;

    // ✅ 测试地址：复制到浏览器直接访问 → http://localhost:8501/msg/send?msg=你的消息内容
    @GetMapping("/send")
    public String sendMsg(@RequestParam String msg) {
        msgSender.send(msg); // 调用发送方法
        return "✅ 发送成功！你的消息：" + msg; // 返回前端提示
    }
}