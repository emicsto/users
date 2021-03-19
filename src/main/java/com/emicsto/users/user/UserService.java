package com.emicsto.users.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserService {
    private final UserRepository userRepository;

    public UserOutputDto getUserByLogin(String login) {
        return new UserOutputDto();
    }
}
