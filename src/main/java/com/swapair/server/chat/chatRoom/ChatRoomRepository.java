package com.swapair.server.chat.chatRoom;

import com.swapair.server.post.Post;
import com.swapair.server.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByPostId(@Param("postId") Long postId);

    ChatRoom findByChatRoomId(@Param("roomId") String roomId);
}
