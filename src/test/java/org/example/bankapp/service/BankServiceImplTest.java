package org.example.bankapp.service;

import org.example.bankapp.controller.BankController;
import org.example.bankapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.util.UUID;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BankController.class)
class BankServiceImplTest {

    @MockBean
    private BankService bankService;

    @Autowired
    MockMvc mockMvc;


    User user;
    UUID userId;

    @Autowired
    private BankServiceImpl bankServiceImpl;

    @BeforeEach
    void setUp() {
        bankService = new BankServiceImpl();
         userId = UUID.randomUUID();
        user = User.builder()
                .accountNumber(userId)
                .username("AmirZiya")
                .password(UUID.randomUUID().toString())
                .amount(1000)
                .build();
    }

    @Test
    void viewBalance()throws Exception {
        mockMvc.perform(get("/bank/balance/{bankId}",user.getAccountNumber())
                        .contentType(MediaType.ALL)
                .accept(MediaType.ALL)).andExpect(status().isOk());
    }

    @Test
    void deposit() {
    }

    @Test
    void withdraw() {
    }

    @Test
    void transfer() {
    }
}