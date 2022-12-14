package com.swapair.server.post;

import com.swapair.server.post.params.PostDetailParams;
import com.swapair.server.post.params.PostSearchParams;
import com.swapair.server.post.params.PostWriteParams2;

import java.util.List;

public interface PostService {
    Long createPost(PostWriteParams2 post);

    void createExcelPost(Post post);

    List<PostSearchParams> getAllPosts();

    PostDetailParams getPost(Long postId);

    List<String> getAllImages();

    List<PostSearchParams> searchPosts(Long categoryId, String searchKey, Filter filter);

}
