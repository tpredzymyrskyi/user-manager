package com.project.usermanager.service;

import com.project.usermanager.model.User;
import com.project.usermanager.repository.UserRepository;
import com.project.usermanager.utill.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public User updateUser(String userId, User newUser) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setAge(newUser.getAge());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
