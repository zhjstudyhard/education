package com.education.util.socket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-10-20-21
 */
@Component
@ServerEndpoint("/webSocket/{userId}")
public class WebSocketUtil {
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketUtil对象。
     */
    private static CopyOnWriteArraySet<WebSocketUtil> webSocketSet = new CopyOnWriteArraySet<WebSocketUtil>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 用户id
     */
    private String userId = "";

    /**
     * 连接建立成功调用的方法
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        //加入set中
        webSocketSet.add(this);
        //添加在线人数
        addOnlineCount();
        System.out.println("新连接接入。当前在线人数为：" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        subOnlineCount();
        System.out.println("有连接关闭。当前在线人数为：" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("客户端发送的消息：" + message);
    }

    /**
     * 暴露给外部的群发
     *
     * @param message
     * @throws IOException
     */
    public static void sendInfo(String message) throws IOException {
        sendAll(message);
    }

    /**
     * 群发
     *
     * @param message
     */
    private static void sendAll(String message) {
        Arrays.asList(webSocketSet.toArray()).forEach(item -> {
            WebSocketUtil customWebSocket = (WebSocketUtil) item;
            //群发
            try {
                customWebSocket.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("----websocket-------有异常啦");
        error.printStackTrace();
    }

    /**
     * 减少在线人数
     */
    private void subOnlineCount() {
        WebSocketUtil.onlineCount--;
    }

    /**
     * 添加在线人数
     */
    private void addOnlineCount() {
        WebSocketUtil.onlineCount++;
    }

    /**
     * 当前在线人数
     *
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 暴露给外部的发送用户信息
     *
     * @param message
     * @throws IOException
     */
    public static void sendInfoByUserId(String message, String userId) throws IOException {
        for (WebSocketUtil socket : webSocketSet) {
            String thisUserId = socket.userId;
            if (userId.equals(thisUserId)) {
                socket.sendMessage(message);
            }
        }
    }

    /**
     * 发送信息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        //获取session远程基本连接发送文本消息
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

}
