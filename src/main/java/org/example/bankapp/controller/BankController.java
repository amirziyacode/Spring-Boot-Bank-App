package org.example.bankapp.controller;


import lombok.RequiredArgsConstructor;
import org.example.bankapp.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    @GetMapping("/bank/balance/{bankId}")
    public ResponseEntity<Long> getBalance(@PathVariable("bankId") UUID bankId) {
        return ResponseEntity.ok(bankService.viewBalance(bankId));
    }

}
