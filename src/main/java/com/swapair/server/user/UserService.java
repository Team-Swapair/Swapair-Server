package com.swapair.server.user;

import com.swapair.server.post.Post;
import com.swapair.server.post.params.PostProfileParams;
import com.swapair.server.user.params.UserSignInParams;
import com.swapair.server.user.params.UserSignUpParams;

import java.util.List;

public interface UserService {
    User createUser(UserSignUpParams userSignUpParams);

    User getUser(Long userId);

    Boolean checkDuplicateEmail(String email);

    Long logIn(UserSignInParams userSignInParams);

    Boolean checkDuplicatePhoneNumber(String phoneNumber);

    List<PostProfileParams> getMyPosts(Long userId);
}

