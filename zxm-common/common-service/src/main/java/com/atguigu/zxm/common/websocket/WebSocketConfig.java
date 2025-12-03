//package com.atguigu.zxm.common.websocket;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        // 注册WebSocket处理器，映射路径为/ws
//        registry.addHandler(new MyWebSocketHandler(), "/ws").setAllowedOrigins("*");
//    }
//
//    // 自定义处理器：处理连接、消息收发
//    public static class MyWebSocketHandler extends TextWebSocketHandler {
//        @Override
//        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//            System.out.println("新客户端连接：" + session.getId());
//            session.sendMessage(new TextMessage("欢迎连接Spring WebSocket服务！"));
//        }
//
//        @Override
//        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//            String clientMsg = message.getPayload();
//            System.out.println("收到消息：" + clientMsg);
//            // 回复客户端
//            session.sendMessage(new TextMessage("服务端已收到：" + clientMsg));
//        }
//
//        @Override
//        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//            System.out.println("客户端断开连接：" + session.getId());
//        }
//    }
//}
