package org.example.bankapp.boot;


import org.assertj.core.api.Assertions;
import org.example.bankapp.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootApp {

    @Autowired
    UserRepository userRepository;

    @Test
    void user_count() {
        Assertions.assertThat(userRepository.count()).isEqualTo(2);
    }
}