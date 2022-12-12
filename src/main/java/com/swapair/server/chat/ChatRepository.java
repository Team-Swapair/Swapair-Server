package com.swapair.server.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

    List<String> findByRoomSeq(@Param("roomId") String roomId);
}
