package org.example.bankapp.boot;


import org.assertj.core.api.Assertions;
import org.example.bankapp.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SpringBootApp {


    @Autowired(required = false)
    UserRepository userRepository;

    @Test
    void user_count() {
        Assertions.assertThat(userRepository.count()).isEqualTo(1);
    }
}