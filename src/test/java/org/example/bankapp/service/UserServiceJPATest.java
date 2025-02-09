package org.example.bankapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bankapp.controller.UserController;
import org.example.bankapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = UserController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class UserServiceJPATest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;



    @Autowired
    ObjectMapper objectMapper;


    private User mocUser;

    @BeforeEach
    void setUp() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        mocUser = User.builder()
                .accountNumber(UUID.randomUUID())
                .username("Amir")
                .password(bCryptPasswordEncoder.encode("Java"))
                .amount(500.0)
                .build();
    }



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
        when(userService.loadUser("Amir","Java")).thenReturn(true);
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"Amir\", \"password\":\"Java\"}"))
                .andExpect(status().isOk());

    }
    @Test
    void login_wrong_password()throws Exception {
        when(userService.loadUser("Amir","wrongPassword")).thenReturn(false);
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"Amir\", \"password\":\"wrongPassword\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void get_user()throws Exception {
        when(userService.getUser("Amir")).thenReturn(mocUser);
        mockMvc.perform(get("/auth/user").param("userName","Amir")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mocUser)))
                .andExpect(status().isOk());
    }
}