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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
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

    UUID bankId;

    @Autowired(required = false)
    BankServiceImpl bankServiceImpl;

    @BeforeEach
    void setUp() {
        bankServiceImpl = new BankServiceImpl();
        bankId = bankServiceImpl.user.getAccountNumber();
    }

    @Test
    void viewBalance()throws Exception {
        when(bankService.viewBalance(bankServiceImpl.user.getAccountNumber())).thenReturn(Optional.of(bankServiceImpl.user.getAmount()));
        mockMvc.perform(get("/bank/balance/{bankId}",bankId).with(httpBasic("Admin", "1234"))
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(bankService).viewBalance(uuidArgumentCaptor.capture());
    }

    @Test
    void deposit()  throws Exception {
        bankServiceImpl.deposit(bankServiceImpl.user.getAccountNumber(), 100.0);
        given(bankService.deposit(any(UUID.class),any(Double.class))).willReturn(bankServiceImpl.user);
        mockMvc.perform(post("/bank/deposit/{bankId}",bankId).with(httpBasic("Admin", "1234"))
                .contentType(objectMapper.writeValueAsString(bankServiceImpl.user))
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":\"100.0\"}"))
                .andExpect(status().isOk());
        assertThat(bankServiceImpl.user.getAmount()).isEqualTo(1100.0);
    }

    @Test
    void withdraw() throws Exception {
        bankServiceImpl.withdraw(bankServiceImpl.user.getAccountNumber(), 100.0);
        given(bankService.withdraw(any(UUID.class),any(Double.class))).willReturn(bankServiceImpl.user);
        mockMvc.perform(post("/bank/deposit/{bankId}",bankId)
                        .contentType(objectMapper.writeValueAsString(bankServiceImpl.user))
                        .content("{\"amount\":\"100.0\"}")
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
     assertThat(bankServiceImpl.user.getAmount()).isEqualTo(900.0);
    }

    @Test
    void transfer() throws Exception {
        given(bankService.withdraw(any(UUID.class),any(Double.class))).willReturn(bankServiceImpl.user);
        mockMvc.perform(post("/bank/transfer/{bankId}",bankId)
                .contentType(objectMapper.writeValueAsString(bankServiceImpl.user))
                .content("{" +
                        "\"accountNumber\":\""+bankId+"\""+","+
                        "\"amount\":\"100.0\"" +
                        "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void not_found_balance() {
        given(bankService.viewBalance(any(UUID.class))).willThrow(RuntimeException.class);
    }
}