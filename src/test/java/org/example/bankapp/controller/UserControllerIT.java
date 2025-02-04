package org.example.bankapp.controller;

import org.example.bankapp.model.MassageResponse;
import org.example.bankapp.model.User;
import org.example.bankapp.model.UserPassword;
import org.example.bankapp.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    User user,user1;
    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
         user = userRepository.findAll().get(0);
    }


    @Test
    void username_available(){
        user.setUsername("Amir");
        assertThatIllegalArgumentException().isThrownBy(() -> userController.register(user));
    }


    @Test
    void createUser() {
        User user1 = User.builder()
                .username("Test")
                .password("1234")
                .build();
        ResponseEntity<MassageResponse> getUser = userController.register(user1);
        assertThat(getUser.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(getUser.getBody()).isNotNull();
        assertThat(getUser.getBody().getMessage()).isEqualTo("Registering Was Successfully !");
    }

    @Test
    void forgotPassword_user() {
        UserPassword userPassword = UserPassword.builder().oldPassword("1234").newPassword("7777").confirmPassword("7777").build();

        ResponseEntity<UserPassword> getUser = userController.forgetPassword(user.getId(),userPassword);
        assertThat(getUser).isNotNull();
        assertThat(getUser.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    void forget_Password_user_not_confirmed() {
        UserPassword userPassword = UserPassword.builder().oldPassword("1111").newPassword("7777").confirmPassword("7777").build();
        assertThatIllegalArgumentException().isThrownBy(() -> userController.forgetPassword(user.getId(),userPassword));
    }

    @Test
    void login_successfully() {
        User  mockUser = User.builder().username("Amir").password("Java").build();
        ResponseEntity<MassageResponse> login = userController.login(mockUser);
        assertThat(login.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void login_unSuccessfully() {
        ResponseEntity<MassageResponse> login = userController.login(user);
        assertThat(login.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(login.getBody()).isEqualTo(new MassageResponse("Invalid username or password!"));

    }
}