package com.chat.ChatApp.WebSocketHandler;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import com.chat.ChatApp.Repositary.JDBCRepositary;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.logging.Logger;

import java.io.IOException;
import java.util.HashMap;

import java.util.*;



@RestController
public class WebSocketHandler extends TextWebSocketHandler {


    JDBCRepositary repo = new JDBCRepositary();

    HashMap<String, WebSocketSession> online = new HashMap<>();

    ObjectMapper om = new ObjectMapper();

    private static final Logger logger = Logger.getLogger(WebSocketHandler.class.getName());


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Extract the URI from the session

    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    }

    @Scheduled(fixedRate = 30000)
    public void sendHeartbeat() throws IOException {
        Set<String> keys = online.keySet();
        for (String key : keys) {
            WebSocketSession session = online.get(key);
            if (session.isOpen()) {
                session.sendMessage(new TextMessage("ping"));
            }
        }
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }




    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // This method is called when the connection is closed.

    }

    @Override
    public boolean supportsPartialMessages() {
        // This method is called to check if partial messages are supported.
        return false; // Return true if you want to support partial messages.
    }
}
