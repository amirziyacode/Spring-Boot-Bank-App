package org.example.bankapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bankapp.controller.BankController;
import org.example.bankapp.model.TransactionsBank;
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
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = BankController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class BankServiceImplTest {

    @MockBean
    private BankService bankService;

    @MockBean
    private TransactionsBankRepo transactionsBankRepo;


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    private BankServiceImpl bankServiceImpl;
    private  TransactionsBank transactionsBank;

    @BeforeEach
    void setUp() {
        bankServiceImpl = new BankServiceImpl();
       transactionsBank = TransactionsBank.builder().userId(1).methodName("ViewBalance").createdDate(LocalDateTime.now()).build();
    }

    @Test
    void viewBalance()throws Exception {
        given(bankService.viewBalance(any(UUID.class))).willReturn(anyDouble());
        mockMvc.perform(get("/bank/balance/{bankId}",bankServiceImpl.user.getAccountNumber())
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }


    @Test
    void get_transactions_bank_not_Found()throws Exception {
        given(transactionsBankRepo.findByUserId(any(Integer.class))).willReturn(new ArrayList<>());
        mockMvc.perform(get("/transactions/{id}",bankServiceImpl.user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
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

    @Test
    void withdraw() throws Exception {
        given(bankService.withdraw(any(UUID.class),anyDouble())).willReturn(bankServiceImpl.withdraw(bankServiceImpl.user.getAccountNumber(),100.0));
        mockMvc.perform(post("/bank/withdrawal/{bankId}",UUID.randomUUID())
                        .content("{\"amount\":\"100.0\"}")
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
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
    void get_transactions_bank()throws Exception {
        given(transactionsBankRepo.findByUserId(bankServiceImpl.user.getId())).willReturn(List.of(transactionsBank));
        mockMvc.perform(get("/transactions/{id}",bankServiceImpl.user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void not_found_balance() {
        given(bankService.viewBalance(any(UUID.class))).willThrow(RuntimeException.class);
    }
}