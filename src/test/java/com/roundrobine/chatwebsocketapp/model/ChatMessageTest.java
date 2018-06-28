package com.roundrobine.chatwebsocketapp.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * created by Dimitar on  Jun, 2018
 **/
@Slf4j
public class ChatMessageTest {

    ChatMessage chatMessage;

    @Before
    public void setUp() throws Exception {

         chatMessage = new ChatMessage();
    }

    @Test
    public void getType() {

        chatMessage.setType(MessageType.CHAT);
        assertEquals(MessageType.CHAT, chatMessage.getType());
    }

    @Test
    public void getContent() {

        String content = "This is a test message!";
        chatMessage.setContent(content);
        assertEquals("This is a test message!", chatMessage.getContent());

    }

    @Test
    public void getSender() {

        String sender = "roundrobine";
        chatMessage.setSender(sender);
        assertEquals("roundrobine", chatMessage.getSender());
    }

    @Test
    public void getTimestamp() {

        Date sentOn = new Date();
        chatMessage.setTimestamp(sentOn);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
           log.error("The sleep was interrupted!");
        }
        assertEquals(true, new Date().after(sentOn));
    }
}