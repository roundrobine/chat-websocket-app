package com.roundrobine.chatwebsocketapp.controller;

import com.roundrobine.chatwebsocketapp.model.ChatMessage;
import com.roundrobine.chatwebsocketapp.model.MessageType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

/**
 * created by Dimitar on  Jun, 2018
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatControllerIT {

    @Value("${local.server.port}")
    private int port;
    private String URL;

    private static final String SEND_MESSAGE_ENDPOINT = "/app/send/message/";
    private static final String SEND_ADD_USER_ENDPOINT = "/app/addUser/";
    private static final String SUBSCRIBE_TO_CHAT_ENDPOINT = "/chat/broadcast/";

    private CompletableFuture<ChatMessage> completableFuture;
    private WebSocketStompClient stompClient;

    @Before
    public void setup() {
        completableFuture = new CompletableFuture<>();
        URL = "ws://localhost:" + port + "/websocket";
        stompClient = new WebSocketStompClient(new SockJsClient(
                Arrays.asList(new WebSocketTransport(new StandardWebSocketClient()))));
    }

    @Test
    public void sendMessage() throws InterruptedException, ExecutionException, TimeoutException {

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {
        }).get(1, TimeUnit.SECONDS);

        stompSession.subscribe(SUBSCRIBE_TO_CHAT_ENDPOINT, new CreateChatStompFrameHandler());
        stompSession.send(SEND_MESSAGE_ENDPOINT, new ChatMessage(MessageType.CHAT, "This is a test message!",
                "roundrobine", new Date()));
        ChatMessage message  = completableFuture.get(5, TimeUnit.SECONDS);
        assertEquals("This is a test message!", message.getContent());
        assertEquals("roundrobine", message.getSender());
        assertEquals(MessageType.CHAT, message.getType());
    }

    @Test
    public void addUser() {
    }


   private class CreateChatStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return ChatMessage.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            completableFuture.complete((ChatMessage) o);
        }
    }
}