package com.project.usermanager.controller;

import com.project.usermanager.model.User;
import com.project.usermanager.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getUserShouldReturnCorrectUser() throws Exception {
        User savedUser = getUser();
        when(userService.getUser(anyString())).thenReturn(savedUser);
        MvcResult mvcResult = mockMvc
                .perform(get("/user/" + savedUser.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        User user = objectMapper.readValue(content, User.class);
        assertEquals(savedUser, user);
    }

    @Test
    public void saveUserShouldSaveUser() throws Exception {
        User userToSave = getUser();
        when(userService.saveUser(userToSave)).thenReturn(userToSave);
        mockMvc.perform(post("/user/").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userToSave)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();
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
