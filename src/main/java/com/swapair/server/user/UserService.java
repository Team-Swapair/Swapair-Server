package com.swapair.server.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(UserParams userParam) {
        User user = new User();
        user.setEmail(userParam.getEmail());
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }
}

