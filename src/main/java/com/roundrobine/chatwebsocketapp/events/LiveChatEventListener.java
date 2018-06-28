package com.roundrobine.chatwebsocketapp.events;

import com.roundrobine.chatwebsocketapp.model.ChatMessage;
import com.roundrobine.chatwebsocketapp.model.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Date;

/**
 * created by roundrobine on  Jun, 2018
 **/

@Slf4j
@Component
public class LiveChatEventListener {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public LiveChatEventListener(SimpMessageSendingOperations simpMessageSendingOperations) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    @EventListener
    public void handleLiveChatConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection from");
    }

    @EventListener
    public void handleLiveChatDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if(username != null) {
            log.info("User Disconnected : " + username);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(MessageType.LEAVE);
            chatMessage.setSender(username);
            chatMessage.setTimestamp(new Date());

            simpMessageSendingOperations.convertAndSend("/chat/broadcast/", chatMessage);
        }
    }
}
