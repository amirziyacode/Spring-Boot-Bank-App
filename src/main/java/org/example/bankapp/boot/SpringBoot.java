package org.example.bankapp.boot;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bankapp.model.User;
import org.example.bankapp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SpringBoot implements CommandLineRunner {

    private  final BCryptPasswordEncoder bCryptPasswordEncoder  = new BCryptPasswordEncoder();

    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadUser();
    }
    private void loadUser(){
       User user = User.builder()
                .accountNumber(UUID.randomUUID())
                .username("Admin")
                .password(bCryptPasswordEncoder.encode("1234"))
                .amount(1000000)
                .build();

       userRepository.save(user);
    }
}
