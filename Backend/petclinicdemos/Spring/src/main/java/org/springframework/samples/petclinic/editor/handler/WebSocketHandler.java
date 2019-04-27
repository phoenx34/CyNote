package org.springframework.samples.petclinic.editor.handler;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.OnError;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;


public class WebSocketHandler {
    
    private static int onlineCount = 0;

    private static CopyOnWriteArraySet<WebSocketHandler> webSocketSet = new CopyOnWriteArraySet<WebSocketHandler>();

    private Session session;
 
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("Number of people online:  " + getOnlineCount());
        try {
            sendMessage("Message");
        } catch (IOException e) {
            System.out.println("IO exception");
        }
    }
    
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("Number of people online: " + getOnlineCount());
    }
    
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message from clinet: " + message);

    }
    
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("error");
        error.printStackTrace();
    }
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }
    
    public static void sendInfo(String message) throws IOException {
        for (WebSocketHandler item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    public static synchronized void addOnlineCount() {
        WebSocketHandler.onlineCount++;
    }
    public static synchronized void subOnlineCount() {
        WebSocketHandler.onlineCount--;
    }
}
