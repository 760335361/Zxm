package com.atguigu.zxm.manager.config;

import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.zxm.manager.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArraySet;

@Configuration
@EnableWebSocket
public class WebSocket implements WebSocketConfigurer {

    @Autowired
    private SysRoleService roleService;

    // 存储上一次数据（对比变化用）
    private String lastData = "";

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册处理器，路径用 /ws（简单好记，避免复杂路径出错）
        registry.addHandler(new TextWebSocketHandler() {

                    // 存储长连接会话（线程安全）
                    private final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

                    // 1. 连接建立（必触发，看得见日志）
                    @Override
                    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                        sessions.add(session);
                        System.out.println("🔗 客户端连接成功！SessionID=" + session.getId() + "，当前连接数=" + sessions.size());
                        session.sendMessage(new TextMessage("连接成功！实时监听中..."));
                    }

                    // 2. 实时接收数据（有数据推送就触发，不用手动调用）
                    @Override
                    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                        String xmlData = message.getPayload().trim();
                        System.out.println("📥 实时收到数据：" + xmlData);

                        // 数据变化判断
                        if (!xmlData.equals(lastData)) {
                            System.out.println("🔔 数据变化，执行保存！");
                            // 保存逻辑（原样保留）
                            SysRole role = new SysRole();
                            role.setRoleCode("朱学敏");
                            role.setRoleName("朱学敏");
                            role.setDescription("润达医疗");
                            roleService.saveSysRole(role);

                            lastData = xmlData;
                            session.sendMessage(new TextMessage("✅ 保存成功：" + role.toString()));
                        } else {
                            System.out.println("📌 数据无变化");
                            session.sendMessage(new TextMessage("❌ 数据无变化，无需保存"));
                        }
                    }

                    // 3. 连接断开（清理会话）
                    @Override
                    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
                        sessions.remove(session);
                        System.out.println("❌ 客户端断开连接！SessionID=" + session.getId());
                    }

                    // 4. 错误处理（打印具体错误）
                    @Override
                    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
                        System.out.println("⚠️  连接错误！SessionID=" + session.getId() + "，错误：" + exception.getMessage());
                        if (session.isOpen()) session.close();
                        sessions.remove(session);
                    }

                }, "/ws") // 连接路径简化为 /ws，减少出错概率
                .setAllowedOriginPatterns("*"); // 允许所有跨域，开发阶段必开
    }
}