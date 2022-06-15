package com.swapair.server.user;

import com.swapair.server.post.Post;
import com.swapair.server.post.params.PostProfileParams;
import com.swapair.server.user.params.UserSignInParams;
import com.swapair.server.user.params.UserSignUpParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"1.1 사용자"})
@RestController
@Controller
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "사용자 정보 입력", notes = "사용자 정보를 입력한다")
    @PostMapping(value = "user")
    public User createUser(@RequestBody UserSignUpParams userSignUpParams) { return userService.createUser(userSignUpParams); }

    @ApiOperation(value = "사용자 조회", notes = "사용자 id로 사용자의 모든 정보를 조회한다")
    @GetMapping(value = "user/{userId}")
    public User getUser(@PathVariable("userId") Long userId){ return userService.getUser(userId); }

    @ApiOperation(value = "중복 이메일 조회", notes = "중복된 이메일이 있는지 조회한다")
    @GetMapping(value = "user/duplicate/email/{email}")
    public Boolean checkDuplicateEmail(@PathVariable("email") String email){ return userService.checkDuplicateEmail(email); }

    @ApiOperation(value = "사용자 로그인", notes = "사용자의 이메일과 비밀번호로 계정 여부를 확인한다")
    @PostMapping(value = "user/login")
    public Long logIn(@RequestBody UserSignInParams userSignInParams) {return userService.logIn(userSignInParams); }

    @ApiOperation(value = "중복 전화번호 조회", notes = "중복된 전화번호가 있는지 조회한다")
    @GetMapping(value = "user/duplicate/phoneNumber/{phoneNumber}")
    public Boolean checkDuplicatePhoneNumber(@PathVariable("phoneNumber") String phoneNumber){ return userService.checkDuplicatePhoneNumber(phoneNumber); }

    @ApiOperation(value = "사용자 게시물 조회", notes = "사용자가 작성한 모든 게시물을 조회한다")
    @GetMapping(value = "user/{userId}/posts")
    public List<PostProfileParams> getMyPosts(@PathVariable("userId") Long userId){ return userService.getMyPosts(userId); }

}
