package com.roundrobine.chatwebsocketapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * created by roundrobine on  Jun, 2018
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    private MessageType type;
    private String content;
    private String sender;
    private Date timestamp;

}
