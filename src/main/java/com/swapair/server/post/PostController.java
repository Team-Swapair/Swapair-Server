package com.swapair.server.post;

import com.swapair.server.post.mapping.PostSearchMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = {"2.1 게시물"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @ApiOperation(value = "모든 게시물 조회", notes = "모든 게시물을 조회한다")
    @GetMapping(value = "posts")
    public List<PostSearchParams> getAllPosts(){ return postService.getAllPosts(); }


}
