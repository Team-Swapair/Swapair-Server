package com.swapair.server.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public void createUser(@RequestBody UserParams userParam) { userService.createUser(userParam); }


}
