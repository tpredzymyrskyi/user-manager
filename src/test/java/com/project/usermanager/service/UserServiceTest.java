package com.project.usermanager.service;

import com.project.usermanager.model.User;
import com.project.usermanager.repository.UserRepository;
import com.project.usermanager.utill.exception.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void updateUserShouldReturnUpdatedUser() {
        User userInDatabase = getUser();
        User requestUser = getUser();
        requestUser.setName("Test2");
        when(userRepository.findById(any())).thenReturn(Optional.of(userInDatabase));
        when(userRepository.save(any())).thenReturn(requestUser);
        User updatedUser = userService.updateUser(requestUser.getId(), requestUser);
        assertEquals(requestUser.getName(), updatedUser.getName());
        assertEquals(userInDatabase.getName(), updatedUser.getName());
    }

    @Test(expected = UserNotFoundException.class)
    public void updateUserShouldThrowExceptionWhenNoUserFound() {
        userService.updateUser("1", getUser());
        when(userRepository.findById(any())).thenThrow(UserNotFoundException.class);
    }

    private User getUser() {
        User user = new User();
        String userId = "1";
        user.setId(userId);
        user.setName("Test");
        user.setAge(10L);
        return user;
    }
}
