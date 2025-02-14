package org.example.bankapp.repository;

import jakarta.transaction.Transactional;
import org.example.bankapp.model.User;
import org.example.bankapp.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private  UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User mockUser = User.builder()
                .accountNumber(UUID.fromString("bfe15392-e7de-4d9d-863d-0c6895d25d5b"))
                .username("admin")
                .password(bCryptPasswordEncoder.encode("1234"))
                .build();
        userRepository.save(mockUser);
    }

    @Test
    @Transactional
    @Rollback
    void findByUsername() {
        User get_user_by_username = userRepository.findByUsername("admin");
        assertEquals("admin", get_user_by_username.getUsername());
        assertTrue(bCryptPasswordEncoder.matches("1234", get_user_by_username.getPassword()));
    }

    @Test
    @Transactional
    @Rollback
    void findByAccountNumber() {
        User get_user_by_accountNumber = userRepository.findByAccountNumber(UUID.fromString("bfe15392-e7de-4d9d-863d-0c6895d25d5b"));
        assertEquals("admin", get_user_by_accountNumber.getUsername());
        assertTrue(bCryptPasswordEncoder.matches("1234", get_user_by_accountNumber.getPassword()));
    }
}