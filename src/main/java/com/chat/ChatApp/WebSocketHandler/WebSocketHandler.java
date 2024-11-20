package com.chat.ChatApp.WebSocketHandler;


import com.chat.ChatApp.Models.Request;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import com.chat.ChatApp.Repositary.JDBCRepositary;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import java.util.Iterator;



@RestController
public class WebSocketHandler extends TextWebSocketHandler {


    JDBCRepositary repo = new JDBCRepositary();

    HashMap<String, WebSocketSession> online = new HashMap<>();

    ObjectMapper om = new ObjectMapper();

    public static String getKeysAsJson(Map<String, WebSocketSession> map) {
        try {
            // Extract the keys from the map
            Set<String> keys = map.keySet();

            // Convert the Set of keys to an array
            String[] keyArray = keys.toArray(new String[0]);

            // Create a new map to store the keys and type in JSON format
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("messageType", "update");  // Add the "type" field
            resultMap.put("list", keyArray);  // Store the key array inside the result map

            // Convert the result map to a JSON string using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(resultMap);  // Return JSON string
        } catch (Exception e) {
            e.printStackTrace();  // Handle exception (e.g., JSON parsing errors)
            return "{}";  // Return an empty JSON object in case of error
        }
    }




    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Extract the URI from the session
        URI uri = session.getUri();
        if (uri != null) {
            // Parse the query parameters
            Map<String, List<String>> queryParams = UriComponentsBuilder.fromUri(uri).build().getQueryParams();
            List<String> unames = queryParams.get("username");

            if (unames != null && !unames.isEmpty()) {
                String username = unames.get(0); // Get the first username
                System.out.println("Connection established with User : " + username);

                online.put(username,session);


                String jsonList = getKeysAsJson(online);

                for(String key : online.keySet())
                {
                    online.get(key).sendMessage(new TextMessage(jsonList));
                }


                // Optionally, you can send a welcome message back to the client
//                session.sendMessage(new TextMessage("Welcome, " + email + "!"));
            }
        }

    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received: " + message.getPayload());
        Request obj = om.readValue(message.getPayload(),Request.class);
        if(obj.getMessageType().equals("message"))
        {
            online.get(obj.getTo()).sendMessage(message);
            System.out.println("Sent!!");
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // This method is called when a transport error occurs.
        System.err.println("Error in session " + session.getId() + ": " + exception.getMessage());
        exception.printStackTrace();
        session.close(CloseStatus.SERVER_ERROR);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // This method is called when the connection is closed.
        System.out.println("Connection closed with session: " + session.getId() + ", status: " + status);
        Iterator<Map.Entry<String, WebSocketSession>> iterator = online.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, WebSocketSession> entry = iterator.next();
            if (entry.getValue().equals(session)) {
                iterator.remove(); // Remove the entry
            }
        }
        String jsonList = getKeysAsJson(online);

        for(String key : online.keySet())
        {
            online.get(key).sendMessage(new TextMessage(jsonList));
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        // This method is called to check if partial messages are supported.
        return false; // Return true if you want to support partial messages.
    }
}
