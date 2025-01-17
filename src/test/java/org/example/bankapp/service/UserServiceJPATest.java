package org.example.bankapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bankapp.controller.UserController;
import org.example.bankapp.model.TransactionsBank;
import org.example.bankapp.model.User;
import org.example.bankapp.repo.TransactionsBankRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private TransactionsBankRepo transactionsBankRepo;
    private User user;
    private TransactionsBank transactionsBank;;


    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1)
                .accountNumber(UUID.randomUUID())
                .username("test")
                .password("test")
                .amount(1000)
                .build();
         transactionsBank =  TransactionsBank.builder()
                .userId(1)
                .amount(user.getAmount())
                .accountNumberFrom(user.getAccountNumber())
                .accountNumberTo(user.getAccountNumber())
                .createdDate(LocalDateTime.now())
                .methodName("test")
                .build();
    }

    @Test
    void save_user()throws Exception {
        given(userService.save(any(User.class))).willReturn(new User());
        mockMvc.perform(post("/register")
                        .content(objectMapper.writeValueAsString(User.builder()
                                .username("Test")
                                .password("Test").build()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    void get_transactions_bank_not_Found()throws Exception {
        given(transactionsBankRepo.findByUserId(any(Integer.class))).willReturn(new ArrayList<>());
        mockMvc.perform(get("/transactions/{id}",user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void get_transactions_bank()throws Exception {
        given(transactionsBankRepo.findByUserId(user.getId())).willReturn(List.of(transactionsBank));
        mockMvc.perform(get("/transactions/{id}",user.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}