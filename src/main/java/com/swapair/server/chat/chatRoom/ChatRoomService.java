package com.swapair.server.chat.chatRoom;

import com.swapair.server.post.Post;
import com.swapair.server.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final PostRepository postRepository;

    public String createRoom(Long postId) {
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .randomId(randomId)
                .postId(postId)
                .build();

        chatRoomRepository.save(chatRoom);
        return randomId;
    }

    public List<ChatRoom> getUserChatRooms(Long userId) {
        List<ChatRoom> chatRoomList = new ArrayList<>();
        List<Long> postIds = postRepository.findIdsByUser_UserId(userId);

        chatRoomList = chatRoomRepository.findInPostIds(postIds);

        return chatRoomList;

    }
}
