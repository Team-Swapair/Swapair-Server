package com.swapair.server.chat.chatRoom;

import com.swapair.server.chat.ChatMessage;
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
        List<Post> posts = postRepository.findByUser_UserId(userId);
        log.info("post is "+posts.size());
        for (Post p : posts) {
            List<ChatRoom> roomlist = chatRoomRepository.findByPostId(p.getPostId());

            log.info("chatroom is "+roomlist.size());
            for (ChatRoom c : roomlist) {
                List<ChatMessage> message = chatRepository.findByRoomSeq(c.getRandomId());
                ChatRoomParams param =  ChatRoomParams.builder()
                        .chatRoomId(c.getChatRoomId())
                        .randomId(c.getRandomId())
                        .postId(p.getPostId())
                        .haveImage(p.getHaveImage())
                        .postTitle(p.getPostTitle()).build();

                if (!message.isEmpty()) {
                    param.setMessage(message.get(0).getMessage());
                }

                chatRoomList.add(param);
            }
        }


        return chatRoomList;

    }

    public List<ChatMessage> getChatMessages(String roomId) {

       return chatRepository.findByRoomSeq(roomId);
    }
}
