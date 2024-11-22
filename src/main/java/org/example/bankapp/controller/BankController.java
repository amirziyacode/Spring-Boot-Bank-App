package org.example.bankapp.controller;


import lombok.RequiredArgsConstructor;
import org.example.bankapp.model.User;
import org.example.bankapp.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    @GetMapping("/bank/balance/{bankId}")
    public ResponseEntity<Optional<Double>> getBalance(@PathVariable("bankId") UUID bankId) {
        return  new ResponseEntity<>(Optional.of(bankService.viewBalance(bankId)).get(), HttpStatus.OK);
    }

    @PostMapping("/bank/withdrawal/{bankId}")
    public ResponseEntity<User> withdrawal(@PathVariable("bankId") UUID bankId, @RequestBody User user) {
        return  ResponseEntity.ok().body(bankService.withdraw(bankId,user.getAmount()));
    }

    @PostMapping("/bank/deposit/{bankId}")
    public ResponseEntity<User> deposit(@PathVariable("bankId") UUID bankId, @RequestBody User user) {
        return  ResponseEntity.ok().body(bankService.deposit(bankId,user.getAmount()));
    }

}
