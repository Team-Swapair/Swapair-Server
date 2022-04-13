package com.swapair.server.post;

import java.util.List;

public interface PostService {
    void createPost(Post post);

    List<Post> getAllPosts();

    Post getPost(Long postId);

    List<Post> getMyPosts(Long userId);

    List<Post> searchPosts(Long categoryId, String searchKey, Filter filter);

}
