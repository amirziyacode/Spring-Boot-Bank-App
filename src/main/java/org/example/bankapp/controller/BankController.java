package org.example.bankapp.controller;


import lombok.RequiredArgsConstructor;
import org.example.bankapp.model.User;
import org.example.bankapp.repo.UserRepository;
import org.example.bankapp.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;
    private  final UserRepository userRepository;

    @GetMapping("/bank/balance/{bankId}")
    public ResponseEntity<Double> getBalance(@PathVariable("bankId") UUID bankId) {
        return ResponseEntity.status(HttpStatus.OK).body(bankService.viewBalance(bankId));
    }

    @PostMapping("/bank/withdrawal/{bankId}")
    public ResponseEntity<User> withdrawal(@PathVariable("bankId") UUID bankId, @RequestBody User user) {
        User byAccountNumber = userRepository.findByAccountNumber(bankId);
        if(byAccountNumber.getAmount() >= user.getAmount()) {
            return  ResponseEntity.ok().body(bankService.withdraw(bankId,user.getAmount()));
        }
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(bankService.withdraw(bankId,user.getAmount()));
    }

    @PostMapping("/bank/deposit/{bankId}")
    public ResponseEntity<User> deposit(@PathVariable("bankId") UUID bankId, @RequestBody User user) {
        return  ResponseEntity.ok().body(bankService.deposit(bankId,user.getAmount()));
    }

    @PostMapping("/bank/transfer/{bankId}")
    public ResponseEntity<User> transfer(@PathVariable("bankId") UUID bankId, @RequestBody User user) {
        return  ResponseEntity.ok().body(bankService.transfer(bankId,user.getAccountNumber(),user.getAmount()));
    }

}
