package org.example.bankapp.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bankapp.model.TransactionsBank;
import org.example.bankapp.model.User;
import org.example.bankapp.repo.TransactionsBankRepo;
import org.example.bankapp.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173") // port react App !!
public class BankController {

    private final BankService bankService;
    private final TransactionsBankRepo transactionsBankRepo;

    @GetMapping("balance/{bankId}")
    public ResponseEntity<Double> getBalance(@PathVariable("bankId") UUID bankId) {
        return ResponseEntity.status(HttpStatus.OK).body(bankService.viewBalance(bankId));
    }

    @PostMapping("withdrawal/{bankId}")
    public ResponseEntity<User> withdrawal(@PathVariable("bankId") UUID bankId, @RequestBody User user) {
        return  ResponseEntity.ok().body(bankService.withdraw(bankId,user.getAmount()));
    }

    @PostMapping("deposit/{bankId}")
    public ResponseEntity<User> deposit(@PathVariable("bankId") UUID bankId, @RequestBody User user) {
        return  ResponseEntity.ok().body(bankService.deposit(bankId,user.getAmount()));
    }

    @PostMapping("transfer/{bankId}")
    public ResponseEntity<User> transfer(@PathVariable("bankId") UUID bankId, @RequestBody User user) {
        return  ResponseEntity.ok().body(bankService.transfer(bankId,user.getAccountNumber(),user.getAmount()));
    }

    @GetMapping("transactions/{id}")
    public ResponseEntity<List<TransactionsBank>> getTransactions(@PathVariable Integer id){
        List<TransactionsBank> trx = transactionsBankRepo.findByUserId(id);
        return ResponseEntity.status(HttpStatus.OK).body(trx);
    }

}
