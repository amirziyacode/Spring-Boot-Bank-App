package org.example.bankapp.controller;


import lombok.RequiredArgsConstructor;
import org.example.bankapp.model.User;
import org.example.bankapp.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    @GetMapping("/bank/balance/{bankId}")
    public ResponseEntity<Double> getBalance(@PathVariable("bankId") UUID bankId) {
        return ResponseEntity.ok(bankService.viewBalance(bankId));
    }

    @PostMapping("/bank/withdrawal/{bankId}")
    public ResponseEntity<User> withdrawal(@PathVariable("bankId") UUID bankId, @RequestBody User user) {
        return  ResponseEntity.ok().body(bankService.withdraw(bankId,user.getAmount()));
    }

}
