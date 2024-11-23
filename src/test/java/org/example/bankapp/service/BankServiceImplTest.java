package org.example.bankapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bankapp.controller.BankController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BankController.class)
class BankServiceImplTest {

    @MockBean
    private BankService bankService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;


    @Autowired(required = false)
    BankServiceImpl bankServiceImpl;

    @BeforeEach
    void setUp() {
        bankServiceImpl = new BankServiceImpl();
    }

    @Test
    void viewBalance()throws Exception {
        when(bankService.viewBalance(bankServiceImpl.user.getAccountNumber())).thenReturn(Optional.of(bankServiceImpl.user.getAmount()));
        mockMvc.perform(get("/bank/balance/{bankId}",bankServiceImpl.user.getAccountNumber())
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(bankService).viewBalance(uuidArgumentCaptor.capture());
    }

    @Test
    void deposit()  throws Exception {
        given(bankService.deposit(any(UUID.class),any(Double.class))).willReturn(bankServiceImpl.user);
        mockMvc.perform(post("/bank/deposit/{bankId}",bankServiceImpl.user.getAccountNumber())
                .contentType(objectMapper.writeValueAsString(bankServiceImpl.user))
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":\"100.0\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void withdraw() throws Exception {
        given(bankService.withdraw(any(UUID.class),any(Double.class))).willReturn(bankServiceImpl.user);
        mockMvc.perform(post("/bank/deposit/{bankId}",bankServiceImpl.user.getAccountNumber())
                        .contentType(objectMapper.writeValueAsString(bankServiceImpl.user))
                        .content("{\"amount\":\"100.0\"}")
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void transfer() {
    }
}