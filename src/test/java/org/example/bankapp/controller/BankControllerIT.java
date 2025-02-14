package org.example.bankapp.controller;

import jakarta.transaction.Transactional;
import org.example.bankapp.model.TransactionsBank;
import org.example.bankapp.model.User;
import org.example.bankapp.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
class BankControllerIT {


    @Autowired
    BankController bankController;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    UserRepository userRepository;


    MockMvc mockMvc;

    User user,userTest,user1;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user = userRepository.findAll().get(0);
        user1 = userRepository.findAll().get(1);
        userTest = User.builder().amount(5000.0).accountNumber(UUID.randomUUID()).build(); // Account Not Found !!
    }

    @Test
    void check_view_Balance(){
        ResponseEntity<Double> balanceJson = bankController.getBalance(user.getAccountNumber());
        assertThat(balanceJson.getBody()).isEqualTo(10000.0);
        assertThat(balanceJson.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @Rollback
    @Transactional
    void check_withdrawal_Account_Balance(){
        User userTest = User.builder().amount(100.0).build();
        ResponseEntity<User> withdrawalJson = bankController.withdrawal(user.getAccountNumber(),userTest);
        assertThat(withdrawalJson.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(withdrawalJson.getBody()).isEqualTo(user);
        assertThat(Objects.requireNonNull(withdrawalJson.getBody()).getAmount()).isEqualTo(9900.0);
    }

    @Test
    @Rollback
    @Transactional
    void check_withdrawal_not_Enough_Balance(){
        User userTest = User.builder().amount(100000.0).build();
        assertThatRuntimeException().isThrownBy(() -> bankController.withdrawal(user.getAccountNumber(),userTest));
    }

    @Test
    @Transactional
    @Rollback
    void check_deposit_Account_Balance(){
        User userTest = User.builder().amount(100000.0).build();
        ResponseEntity<User> depositJson = bankController.deposit(user.getAccountNumber(),userTest);
        assertThat(depositJson.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(depositJson.getBody()).isEqualTo(user);
        assertThat(Objects.requireNonNull(depositJson.getBody()).getAmount()).isEqualTo(110000.0);
    }
    @Test
    @Transactional
    @Rollback
    void check_transfer_Account_Balance(){
        User user1 = userRepository.findAll().get(1);
        User userTest = User.builder().amount(5000.0).accountNumber(user1.getAccountNumber()).build();
        ResponseEntity<User> transferJson = bankController.transfer(user.getAccountNumber(),userTest);
        assertThat(transferJson.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(transferJson.getBody()).isEqualTo(user);
        assertThat(Objects.requireNonNull(transferJson.getBody()).getAmount()).isEqualTo(5000.0);
        assertThat(user1.getAmount()).isEqualTo(5500.0);

    }


    @Test
    void get_Transactions_not_found() {
        assertThatRuntimeException().isThrownBy(() -> bankController.getTransactions(99));
    }

    @Test
    @Rollback
    @org.springframework.transaction.annotation.Transactional
    void get_Transactions() {
        bankController.getBalance(user.getAccountNumber()); // for add a Transactions !!!
        ResponseEntity<List<TransactionsBank>> getTrx = bankController.getTransactions(user.getId());
        assertThat(getTrx.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void runTimeException_with_transfer(){
        assertThatRuntimeException().isThrownBy(() -> bankController.transfer(user1.getAccountNumber(),userTest));
    }

    @Test
    void IllegalArgumentException_with_transfer(){
        assertThatRuntimeException().isThrownBy(() -> bankController.transfer(user1.getAccountNumber(),userTest));
    }

    @Test
    void withdraw_greater_than_Account_Balance(){
        assertThatRuntimeException().isThrownBy(() -> bankController.withdrawal(user1.getAccountNumber(),userTest));
    }
}