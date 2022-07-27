package com.bezkoder.spring.datajpa.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import com.bezkoder.spring.datajpa.model.WebSocketClient;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/{machineId}")
@Component
public class WebSocketService {
    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);
    private static int onlineCount = 0;
    private static ConcurrentHashMap<String, WebSocketClient> webSocketMap = new ConcurrentHashMap<>();
    private Session session;
    private String machineId = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("machineId") String machineId) {
        if (!webSocketMap.containsKey(machineId)) {
            addOnlineCount();
        }
        this.session = session;
        this.machineId = machineId;
        WebSocketClient client = new WebSocketClient();
        client.setSession(session);
        client.setUri(session.getRequestURI().toString());
        webSocketMap.put(machineId, client);
        log.info("--------------------------------");
        log.info("Machine Connected:" + machineId + " Current Online machine:" + getOnlineCount());
        try {
            sendMessage("Connected Success");
        } catch (IOException e) {
            log.error("Machine " + machineId + "network unexpected error");
        }
    }

    @OnClose
    public void OnClose() {
        if (webSocketMap.containsKey(machineId)) {
            webSocketMap.remove(machineId);
            if (webSocketMap.size() > 0) {
                subOnlineCount();
            }
        }
        log.info("--------------------------------");
        log.info(machineId + "machine Exited,Current Online machine is " + getOnlineCount());
    }

    @OnMessage
    public void OnMessage(String message, Session session) {
        log.info("Receive User Information" + machineId + "Message:" + message);
        if (StringUtils.isNotBlank(message)) {

        }
    }

    @OnError
    public void OnError(Session session, Throwable error) {
        log.error("Machine error" + this.machineId + ",Reason:" + error.getMessage());
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        synchronized (session) {
            this.session.getBasicRemote().sendText(message);
        }
    }

    public static void sendMessage(String machineId, String message) {
        try {
            WebSocketClient webSocketClient = webSocketMap.get(machineId);
            if (webSocketClient != null) {
                webSocketClient.getSession().getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketService.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketService.onlineCount--;
    }

    public static void setOnlineCount(int onlineCount) {
        WebSocketService.onlineCount = onlineCount;
    }

    public static ConcurrentHashMap<String, WebSocketClient> getWebSocketMap() {
        return webSocketMap;
    }

    public static void setWebSocketMap(ConcurrentHashMap<String, WebSocketClient> webSocketMap) {
        WebSocketService.webSocketMap = webSocketMap;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}