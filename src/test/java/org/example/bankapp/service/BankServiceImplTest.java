package org.example.bankapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bankapp.controller.BankController;
import org.example.bankapp.model.User;
import org.example.bankapp.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = BankController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class BankServiceImplTest {

    @MockBean
    private BankService bankService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    private BankServiceImpl bankServiceImpl;

    @BeforeEach
    void setUp() {
        bankServiceImpl = new BankServiceImpl();
    }

    @Test
    void viewBalance()throws Exception {
        given(bankService.viewBalance(any(UUID.class))).willReturn(anyDouble());
        mockMvc.perform(get("/bank/balance/{bankId}",bankServiceImpl.user.getAccountNumber())
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void deposit()  throws Exception {
        given(bankService.deposit(any(UUID.class),anyDouble())).willReturn(bankServiceImpl.user);
        mockMvc.perform(post("/bank/deposit/{bankId}",bankServiceImpl.user.getAccountNumber())
                .contentType(objectMapper.writeValueAsString(bankServiceImpl.user))
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":\"100.0\"}"))
                .andExpect(status().isOk());
    }

//    @Test
//    void withdraw() throws Exception {
//        given(bankService.withdraw(any(UUID.class),anyDouble())).willReturn(bankServiceImpl.withdraw(bankServiceImpl.user.getAccountNumber(),100.0));
//        mockMvc.perform(post("/bank/withdrawal/{bankId}",UUID.randomUUID())
//                        .content("{\"amount\":\"100.0\"}")
//                .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//    }
//    @Test
//    void withdraw_greater_than_balance() throws Exception {
//        bankServiceImpl.withdraw(bankServiceImpl.user.getAccountNumber(), 2000.0);
//        given(bankService.withdraw(any(UUID.class),any(Double.class))).willReturn(bankServiceImpl.user);
//        mockMvc.perform(post("/bank/withdrawal/{bankId}",bankId)
//                        .contentType(objectMapper.writeValueAsString(bankServiceImpl.user))
//                        .content("{\"amount\":\"2000.0\"}")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isForbidden());
//    }
    @Test
    void transfer() throws Exception {
        mockMvc.perform(post("/bank/transfer/{bankId}",bankServiceImpl.user.getAccountNumber())
                .contentType(objectMapper.writeValueAsString(bankServiceImpl.user))
                .content("{" +
                        "\"accountNumber\":\""+bankServiceImpl.user.getAccountNumber()+"\""+","+
                        "\"amount\":\"100.0\"" +
                        "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void not_found_balance() {
        given(bankService.viewBalance(any(UUID.class))).willThrow(RuntimeException.class);
    }
}