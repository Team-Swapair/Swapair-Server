package com.swapair.server.post;

import com.swapair.server.post.params.PostDetailParams;
import com.swapair.server.post.params.PostSearchParams;

import java.util.List;

public interface PostService {
    void createPost(Post post);

    List<PostSearchParams> getAllPosts();

    PostDetailParams getPost(Long postId);

    List<Post> getMyPosts(Long userId);

    List<PostSearchParams> searchPosts(Long categoryId, String searchKey, Filter filter);

}
