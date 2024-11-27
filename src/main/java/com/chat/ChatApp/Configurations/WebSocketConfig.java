package com.chat.ChatApp.Configurations;

import com.chat.ChatApp.Repositary.JDBCRepositary;
import com.chat.ChatApp.WebSocketHandler.WebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.EOFException;
import java.net.URI;
import java.util.*;
import java.util.logging.Logger;


class MsgFormat{
    String fromID;
    String toID;
    String content;

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getToID() {
        return toID;
    }

    public MsgFormat() {
    }

    public void setToID(String toID) {
        this.toID = toID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public MsgFormat(String fromID, String toID, String content) {
        this.fromID = fromID;
        this.toID = toID;
        this.content = content;
    }
}


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    JDBCRepositary repo = new JDBCRepositary();

    HashMap<String, WebSocketSession> online = new HashMap<>();

    ObjectMapper om = new ObjectMapper();

    private static final Logger logger = Logger.getLogger(WebSocketHandler.class.getName());

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new TextWebSocketHandler() {

                    @Override
                    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                        URI uri = session.getUri();
                        if (uri != null) {
                            // Parse the query parameters
                            Map<String, List<String>> queryParams = UriComponentsBuilder.fromUri(uri).build().getQueryParams();
                            List<String> unames = queryParams.get("username");

                            if (unames != null && !unames.isEmpty()) {
                                String username = unames.get(0); // Get the first username
                                session.sendMessage(new TextMessage("Connection established with User : " + username));
                                online.put(username,session);
                                Set<String> keys = online.keySet();
                                System.out.println(keys);
                            }
                        }
                    }

                    @Override
                    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                        MsgFormat msg = om.readValue(message.getPayload(), MsgFormat.class);

                        // Check if recipient is online
                        WebSocketSession recipientSession = online.get(msg.getToID());
                        if (recipientSession != null && recipientSession.isOpen()) {
                            recipientSession.sendMessage(message);
                            logger.info("Message sent to " + msg.getToID());
                        } else {
                            logger.info(msg.getToID() + " is not online. Message not delivered.");
                            session.sendMessage(new TextMessage("User " + msg.getToID() + " is offline."));
                        }
                    }

                    @Override
                    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
                        if (exception instanceof EOFException) {
                            logger.info("Client disconnected abruptly: Session ID = " + session.getId());
                        } else {
                            logger.info("Error in session " + session.getId());
                        }

                        // Clean up the session
                        if (session.isOpen()) {
                            session.close(CloseStatus.SERVER_ERROR);
                        }
                        removeSession(session);
                    }

                    private void removeSession(WebSocketSession session) {
                        online.entrySet().removeIf(entry -> entry.getValue().equals(session));
                        logger.info("Session removed: " + session.getId());
                        Set<String> keys = online.keySet();
                        System.out.println(keys);
                    }

                    @Override
                    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
                        System.out.println("Connection closed with session: " + session.getId() + ", status: " + status);
                        Iterator<Map.Entry<String, WebSocketSession>> iterator = online.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, WebSocketSession> entry = iterator.next();
                            if (entry.getValue().equals(session)) {
                                iterator.remove(); // Remove the entry
                            }
                        }
                    }

                    @Override
                    public boolean supportsPartialMessages() {
                        // Indicates if partial messages are supported
                        return false;  // Default is false; change if needed.
                    }
                }, "/chat")
                .setAllowedOrigins("*");  // Allow all origins; specify trusted domains in production.
    }
}
