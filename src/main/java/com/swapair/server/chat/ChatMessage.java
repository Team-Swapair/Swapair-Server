package com.swapair.server.chat;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
@Entity
@Table(name="chatMessage")
public class ChatMessage {
    @Id
    private String roomSeq;

    @Column
    private String sender;

    @Column
    private String message;
}