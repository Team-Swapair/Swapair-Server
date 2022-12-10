package com.swapair.server.chat;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMessage {
    private String roomSeq;
    private String sender;
    private String message;
}