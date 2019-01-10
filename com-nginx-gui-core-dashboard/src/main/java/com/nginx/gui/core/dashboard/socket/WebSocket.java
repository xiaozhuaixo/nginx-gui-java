package com.nginx.gui.core.dashboard.socket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author: hengbin_wu
 * @Date: 2019/1/7 11:52
 * @Description:
 */
@Component
@ServerEndpoint(value = "/Dashboard")
public class WebSocket {
    /**
     * 线程安全set ,用来存储每个客户端对应的webSocket对象
     */
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 客户端连接的会话
     */
    private Session session;

    /**
     * 连接建立成功的调用方法
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);
    }

    /**
     * 收到客户端信息
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message , Session session){
        System.out.println(message);
    }

    /**
     * 发送信息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(){
        System.out.println("连接关闭");
        webSocketSet.remove(this);
    }

    /**
     * 群发
     */
    public static void sendAll(String message){
        webSocketSet.forEach(socket -> {
            socket.sendMessage(message);
        });
    }
}
