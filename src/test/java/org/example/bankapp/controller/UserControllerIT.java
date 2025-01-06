package org.example.bankapp.controller;

import org.example.bankapp.model.TransactionsBank;
import org.example.bankapp.model.User;
import org.example.bankapp.model.UserPassword;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserControllerIT {


    @Autowired
    UserController userController;

    @Autowired
    BankController bankController;

    @Autowired
    UserRepository userRepository;

    User user;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
         user = userRepository.findAll().get(0);
    }

    @Test
    void get_Transactions_not_found() {
        ResponseEntity<List<TransactionsBank>> getUser = userController.getTransactions(99);
        assertThat(getUser.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void username_available(){
        user.setUsername("Amir");
        assertThatIllegalArgumentException().isThrownBy(() -> userController.register(user));
    }

    @Test
    @Rollback
    @Transactional
    void get_Transactions() {
        bankController.getBalance(user.getAccountNumber()); // for add a Transactions !!!
        ResponseEntity<List<TransactionsBank>> getTrx = userController.getTransactions(user.getId());
        assertThat(getTrx.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void createUser() {
        User user1 = User.builder()
                .username("Test")
                .password("1234")
                .build();
        ResponseEntity<User> getUser = userController.register(user1);
        assertThat(getUser.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(getUser.getBody()).isNotNull();
        assertThat(getUser.getBody().getUsername()).isEqualTo(user1.getUsername());
        assertThat(getUser.getBody().getPassword()).isEqualTo(user1.getPassword());
    }

    @Test
    void forgotPassword_user() {
        UserPassword userPassword = UserPassword.builder().oldPassword("1234").newPassword("7777").confirmPassword("7777").build();

        ResponseEntity<UserPassword> getUser = userController.forgetPassword(userPassword,user.getId());
        assertThat(getUser).isNotNull();
        assertThat(getUser.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    void forget_Password_user_not_Accepted() {
        UserPassword userPassword = UserPassword.builder().oldPassword("1111").newPassword("7777").confirmPassword("7777").build();
        ResponseEntity<UserPassword> getUser = userController.forgetPassword(userPassword,user.getId());
        assertThat(getUser).isNotNull();
        assertThatIllegalArgumentException().isThrownBy(() -> userController.forgetPassword(userPassword,user.getId()));
    }
}