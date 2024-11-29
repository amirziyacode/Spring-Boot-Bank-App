package org.example.bankapp.boot;

import jakarta.transaction.Transactional;
import org.example.bankapp.model.User;
import org.example.bankapp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@SpringBootApplication
public class SpringBoot implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
//        loadUser();
    }
    private void loadUser(){
       User user = User.builder()
                .accountNumber(UUID.randomUUID())
                .username("AmirZiya")
                .password(UUID.randomUUID().toString())
                .amount(1000000)
                .build();

       userRepository.save(user);
    }
}
