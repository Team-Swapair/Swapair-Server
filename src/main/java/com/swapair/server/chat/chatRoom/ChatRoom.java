package com.swapair.server.chat.chatRoom;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="chatroom")
public class ChatRoom {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long chatRoomId;

        @Column
        private Long postId;

        @Column
        private String randomId;

}
