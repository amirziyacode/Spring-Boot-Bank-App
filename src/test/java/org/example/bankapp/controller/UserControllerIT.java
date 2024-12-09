package org.example.bankapp.controller;

import jakarta.transaction.Transactional;
import lombok.val;
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
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserControllerIT {


    @Autowired
    UserController userController;

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
    void getUser() {
        ResponseEntity<User> getUser = userController.getUser(user.getId());
        assertThat(getUser).isNotNull();
        assertThat(getUser.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void createUser() {
        val user1 = User.builder()
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
    @Rollback
    @Transactional
    void updateUser() {
        user.setUsername("Test");
        user.setPassword("1234");
        ResponseEntity<User> getUpdateUser = userController.updateUser(user.getId(), user);
        assertThat(getUpdateUser.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getUpdateUser.getBody()).isNotNull();
        assertThat(getUpdateUser.getBody().getUsername()).isEqualTo(user.getUsername());
    }
    @Test
    void getUserNotFound() {
        ResponseEntity<User> getUser = userController.getUser(999);
        assertThat(getUser.getBody()).isEqualTo(null);
        assertThat(getUser.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}