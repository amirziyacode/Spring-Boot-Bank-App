package org.example.bankapp.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bankapp.model.TransactionsBank;
import org.example.bankapp.model.User;
import org.example.bankapp.model.UserPassword;
import org.example.bankapp.repo.TransactionsBankRepo;
import org.example.bankapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TransactionsBankRepo transactionsBankRepo;


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid User user) {
        User save = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @PostMapping("user/forgetPassword/{id}")
    public ResponseEntity<UserPassword> forgetPassword(@PathVariable Integer id,@RequestBody @Valid UserPassword user) {
        userService.forgetPassword(id, user);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<List<TransactionsBank>> getTransactions(@PathVariable Integer id){
        List<TransactionsBank> trx = transactionsBankRepo.findByUserId(id);
        if(!(trx.isEmpty()) ) {
            return ResponseEntity.status(HttpStatus.OK).body(trx);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
