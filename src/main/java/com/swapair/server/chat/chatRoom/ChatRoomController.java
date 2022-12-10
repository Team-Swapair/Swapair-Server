package com.swapair.server.chat.chatRoom;

import com.swapair.server.post.params.PostWriteParams2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Api(tags = {"4.1 채팅방"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @ApiOperation(value = "채팅방 생성", notes = "채팅방법을 생성한다.")
    @GetMapping(value = "chatroom/createroom/{postId}")
    public String createRoom(@PathVariable("postId") Long postId){
        return chatRoomService.createRoom(postId);
    }

    @ApiOperation(value = "채팅방 조회", notes = "유저ID당 채팅방을 조회한다..")
    @GetMapping(value = "chatroom/{userId}")
    public List<ChatRoom> getUserCharRooms(@PathVariable("userId") Long userId){
        return chatRoomService.getUserChatRooms(userId);
    }
}
