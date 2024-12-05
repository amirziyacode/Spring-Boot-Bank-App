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
import java.util.Optional;

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
        ResponseEntity<Optional<Double>> balance = bankController.getBalance(user.getAccountNumber());
        assertThat(balance.getBody()).isPresent();
        assertThat(balance.getBody().get()).isEqualTo(1000000.0);
        assertThat(balance.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Rollback
    @Transactional
    void check_withdrawal_Account_Balance(){
        User userTest = User.builder().amount(100000.0).build();
        ResponseEntity<User> withdrawal = bankController.withdrawal(user.getAccountNumber(),userTest);
        assertThat(withdrawal.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(withdrawal.getBody()).isEqualTo(user);
        assertThat(Objects.requireNonNull(withdrawal.getBody()).getAmount()).isEqualTo(900000.0);
    }

    @Test
    void check_deposit_Account_Balance(){

    }
}