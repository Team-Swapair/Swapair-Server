package com.swapair.server.post;

import com.swapair.server.post.params.PostDetailParams;
import com.swapair.server.post.params.PostSearchParams;
import com.swapair.server.post.params.PostWriteParams2;
import com.swapair.server.post.params.SearchKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"2.1 게시물"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:3000",  allowedHeaders = "*")
public class PostController {
    private final PostService postService;

    @ApiOperation(value = "모든 게시물 조회", notes = "모든 게시물을 조회한다")
    @GetMapping(value = "posts")
    public List<PostSearchParams> getAllPosts(){ return postService.getAllPosts(); }

    @ApiOperation(value = "게시물 조회", notes = "게시물 id로 게시물을 조회한다")
    @GetMapping(value = "post/{postId}")
    public PostDetailParams getPost(@PathVariable("postId") Long postId){
        return postService.getPost(postId);
    }

    @ApiOperation(value = "게시물 검색", notes = "카테고리, 검색어, 필터로 게시물을 조회한다")
    @PostMapping(value = "search/post")
    public List<PostSearchParams> getPostWithSearchKey(@RequestBody SearchKey searchKey){
        return postService.searchPosts(searchKey.getCategoryId(), searchKey.getKeyword(), searchKey.getFilter());
    }

    @ApiOperation(value = "게시물 작성", notes = "게시물은 저장한다.")
    @PostMapping(value = "post/postwrite")
    public Long createPost(@RequestBody PostWriteParams2 postWriteParams2){
        return postService.createPost(postWriteParams2);
    }

    @ApiOperation(value = "게시물 이미지 조회", notes = "모든 이미지를 조회한다.")
    @GetMapping(value = "post/images")
    public List<String> getImages(){
        return postService.getAllImages();
    }

}
