package com.swapair.server.user;

import com.swapair.server.post.Post;
import com.swapair.server.post.params.PostProfileParams;
import com.swapair.server.user.params.UserSignInParams;
import com.swapair.server.user.params.UserSignUpParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public void createUser(UserSignUpParams userSignUpParams){
        User user = new User();
        user.setEmail(userSignUpParams.getEmail());
        user.setPassword(userSignUpParams.getPassword());
        user.setPhoneNumber(userSignUpParams.getPhoneNumber());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public User getUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
        return user;
    }

    @Override
    public Boolean checkDuplicateEmail(String email){
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    @Override
    public Long logIn(UserSignInParams userSignInParams) {
        User user = userRepository.findByEmail(userSignInParams.getEmail());
        if(user == null) return -1L;
        if (user.getPassword().equals(userSignInParams.getPassword())) {
            return user.getUserId();
        }else{
            return -1L;
    }        }


    @Override
    public Boolean checkDuplicatePhoneNumber(String phoneNumber){
        User user = userRepository.findByPhoneNumber(phoneNumber);
        return user != null;
    }

    @Override
    public List<PostProfileParams> getMyPosts(Long userId){
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
        List<Post> myPosts = user.getMyPosts();
        List<PostProfileParams> postProfileParamsList = new ArrayList<>();

        for(Post p: myPosts){
            PostProfileParams postProfileParams = new PostProfileParams();
            postProfileParams.setPostId(p.getPostId());
            postProfileParams.setPostTitle(p.getPostTitle());
            postProfileParams.setPostCategory(p.getPostCategory().getCategoryName());
            postProfileParamsList.add(postProfileParams);
        }
        return postProfileParamsList;
    }

}
