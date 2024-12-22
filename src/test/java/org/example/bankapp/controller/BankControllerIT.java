package org.example.bankapp.controller;

import jakarta.transaction.Transactional;
import org.example.bankapp.model.User;
import org.example.bankapp.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.Objects;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class BankControllerIT {


    @Autowired
    BankController bankController;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    UserRepository userRepository;


    MockMvc mockMvc;

    User user;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user = userRepository.findAll().get(0);
    }

    @Test
    void check_view_Balance(){
        ResponseEntity<Double> balanceJson = bankController.getBalance(user.getAccountNumber());
        assertThat(balanceJson.getBody()).isEqualTo(1000000.0);
        assertThat(balanceJson.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @Rollback
    @Transactional
    void check_withdrawal_Account_Balance(){
        User userTest = User.builder().amount(100000.0).build();
        ResponseEntity<User> withdrawalJson = bankController.withdrawal(user.getAccountNumber(),userTest);
        assertThat(withdrawalJson.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(withdrawalJson.getBody()).isEqualTo(user);
        assertThat(Objects.requireNonNull(withdrawalJson.getBody()).getAmount()).isEqualTo(900000.0);
    }

    @Test
    @Transactional
    @Rollback
    void check_deposit_Account_Balance(){
        User userTest = User.builder().amount(100000.0).build();
        ResponseEntity<User> depositJson = bankController.deposit(user.getAccountNumber(),userTest);
        assertThat(depositJson.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(depositJson.getBody()).isEqualTo(user);
        assertThat(Objects.requireNonNull(depositJson.getBody()).getAmount()).isEqualTo(1100000.0);
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
        assertThat(Objects.requireNonNull(transferJson.getBody()).getAmount()).isEqualTo(995000);
        assertThat(user1.getAmount()).isEqualTo(5500.0);

    }
}