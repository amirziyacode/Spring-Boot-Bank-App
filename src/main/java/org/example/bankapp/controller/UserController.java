package org.example.bankapp.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bankapp.model.TransactionsBank;
import org.example.bankapp.model.User;
import org.example.bankapp.model.UserPassword;
import org.example.bankapp.repo.TransactionsBankRepo;
import org.example.bankapp.repo.UserRepository;
import org.example.bankapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final TransactionsBankRepo transactionsBankRepo;

    private final BCryptPasswordEncoder bCryptPasswordEncoder  = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User save = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @PostMapping("user/forgetPassword/{id}")
    public ResponseEntity<User> forgetPassword(@RequestBody UserPassword user, @PathVariable Integer id) {
        Optional<User> userId = userRepository.findById(id);
        if(bCryptPasswordEncoder.matches(user.getOldPassword(),userId.get().getPassword()) && user.getNewPassword().equals( user.getConfirmPassword())) {
            userService.forgetPassword(id,user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userId.get());
        }
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(userId.get());
    }

    @GetMapping("/trx/{id}")
    public ResponseEntity<List<TransactionsBank>> getTransactions(@PathVariable Integer id){
        List<TransactionsBank> trx = transactionsBankRepo.findByUserId(id);
        if(trx != null){
            return ResponseEntity.status(HttpStatus.OK).body(trx);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
