package com.swapair.server.post;

import org.springframework.stereotype.Repository;

import java.util.List;

public class PostRepositoryImpl implements CustomPostRepository{
    @Override
    public List<Post> customMethod() {
        return null;
    }
}
