package com.swapair.server.chat.chatRoom;

import com.swapair.server.chat.ChatRepository;
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
    private final ChatRepository chatRepository;

    public String createRoom(Long postId) {
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .randomId(randomId)
                .postId(postId)
                .build();

        chatRoomRepository.save(chatRoom);
        return randomId;
    }

    public List<ChatRoomParams> getUserChatRooms(Long userId) {
        List<ChatRoomParams> chatRoomList = new ArrayList<>();
        List<Post> posts = postRepository.findIdsByUser_UserId(userId);

        for (Post p : posts) {
            List<ChatRoom> roomlist = chatRoomRepository.findByPostId(p.getPostId());

            for (ChatRoom c : roomlist) {
                List<String> message = chatRepository.findByRoomSeq(c.getRandomId());
                chatRoomList.add(ChatRoomParams.builder()
                        .chatRoomId(c.getChatRoomId())
                        .randomId(c.getRandomId())
                        .postId(p.getPostId())
                        .haveImage(p.getHaveImage())
                                .message(message.get(0))
                        .postTitle(p.getPostTitle()).build());
            }
        }


        return chatRoomList;

    }
}
