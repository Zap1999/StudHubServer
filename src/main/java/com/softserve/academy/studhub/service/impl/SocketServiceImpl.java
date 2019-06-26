package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.coders.SocketMessageEncoder;
import com.softserve.academy.studhub.entity.SocketMessage;
import com.softserve.academy.studhub.service.SocketService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SocketServiceImpl implements SocketService {

    private static Map<Integer, WebSocketSession> sessionIdMap = new ConcurrentHashMap<>();
    private SocketMessageEncoder messageEncoder = new SocketMessageEncoder();

    private static final SocketMessage CONNECTED_MESSAGE = new SocketMessage("Connected successfully.");
    private static final SocketMessage NOT_CONNECTED_MESSAGE = new SocketMessage("Connected unsuccessfully. Access denied.");

    @Override
    public void addSession(Integer id, WebSocketSession session) {
        sessionIdMap.put(id, session);
    }

    @Override
    public void sendNotification(Integer userId, TextMessage message) throws IOException {
        if (sessionIdMap.containsKey(userId)) {
            sessionIdMap.get(userId).sendMessage(new TextMessage(message.getPayload()));
        }
    }

    @Override
    public void sendGreetings(WebSocketSession session, Integer textId) throws EncodeException, IOException {

        if (textId.equals(1)) {
            session.sendMessage(new TextMessage(messageEncoder.encode(CONNECTED_MESSAGE)));
        } else {
            session.sendMessage(new TextMessage(messageEncoder.encode(NOT_CONNECTED_MESSAGE)));
        }

    }

    @Override
    public void removeSession(WebSocketSession session) {
        sessionIdMap.values().remove(session);
    }

    @Override
    public void sendCustomMessage(WebSocketSession session, String msg) throws EncodeException, IOException {
        SocketMessage message = new SocketMessage(msg);
        session.sendMessage(new TextMessage(messageEncoder.encode(message)));
    }
}
