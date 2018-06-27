package com.roundrobine.chatwebsocketapp.controller;

import com.roundrobine.chatwebsocketapp.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * created by roundrobine on  Jun, 2018
 **/


@Controller
public class ChatController {

    @MessageMapping("/send/message/")
    @SendTo("/chat/broadcast/")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/addUser/")
    @SendTo("/chat/broadcast/")
    public ChatMessage addUser(ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {

        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}
