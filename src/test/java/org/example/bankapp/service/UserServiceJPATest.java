package org.example.bankapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bankapp.controller.UserController;
import org.example.bankapp.model.MassageResponse;
import org.example.bankapp.model.User;
import org.example.bankapp.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = UserController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class UserServiceJPATest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;


    @Autowired
    ObjectMapper objectMapper;

    @Test
    void save_user()throws Exception {
        given(userService.save(any(User.class))).willReturn(new User());
        mockMvc.perform(post("/auth/register")
                        .content(objectMapper.writeValueAsString(User.builder()
                                .username("Test")
                                .password("Test").build()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void login_user()throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{" +
                                        "\"username\":\""+"Amir"+"\""+","+
                                        "\"password\":\"Java\"" +
                                        "}"
                        )
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }
}