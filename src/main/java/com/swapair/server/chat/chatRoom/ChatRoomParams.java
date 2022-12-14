package com.swapair.server.chat.chatRoom;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomParams {
    private Long chatRoomId;

    private Long postId;

    private String randomId;

    private String haveImage;

    private String postTitle;

    private String message;
}
