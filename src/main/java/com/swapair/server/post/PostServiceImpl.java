package com.swapair.server.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    @Override
    public void createPost(Post post) {

    }

    @Override
    public List<Post> getAllPosts() {
        return null;
    }

    @Override
    public Post getPost(Long postId) {
        return null;
    }

    @Override
    public List<Post> getMyPosts(Long userId) {
        return null;
    }

    @Override
    public List<Post> searchPosts(Long categoryId, String searchKey, Filter filter) {
        return null;
    }
}
