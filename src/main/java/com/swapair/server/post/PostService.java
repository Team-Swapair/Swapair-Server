package com.swapair.server.post;

import com.swapair.server.post.mapping.PostSearchMapping;

import java.util.List;

public interface PostService {
    void createPost(Post post);

    List<PostSearchParams> getAllPosts();

    Post getPost(Long postId);

    List<Post> getMyPosts(Long userId);

    List<Post> searchPosts(Long categoryId, String searchKey, Filter filter);

}
