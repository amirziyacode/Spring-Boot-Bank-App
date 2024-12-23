package org.example.bankapp.service;

import org.example.bankapp.model.TransactionsBank;
import org.example.bankapp.model.User;
import org.example.bankapp.repo.TransactionsBankRepo;
import org.example.bankapp.repo.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Primary
public class BankServiceImplJPA implements BankService {

    final
    UserRepository userRepository;

    final
    TransactionsBankRepo transactionsBankRepo;

    public BankServiceImplJPA(UserRepository userRepository, TransactionsBankRepo transactionsBankRepo) {
        this.userRepository = userRepository;
        this.transactionsBankRepo = transactionsBankRepo;
    }

    @Override
    public Double viewBalance(UUID accountId) {
        double amount = userRepository.findByAccountNumber(accountId).getAmount();
        TransactionsBank balance = TransactionsBank.builder()
                .accountNumberTo(accountId)
                .amount(amount)
                .accountNumberFrom(accountId)
                .userId(userRepository.findByAccountNumber(accountId).getId())
                .methodName("ViewBalance")
                .createdDate(LocalDateTime.now())
                .build();
        transactionsBankRepo.save(balance);
        return amount;
    }

    @Override
    public User deposit(UUID accountId, Double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount cannot be negative or zero !!");
        }
        User user = userRepository.findByAccountNumber(accountId);
        TransactionsBank deposit = TransactionsBank.builder()
                .accountNumberTo(accountId)
                .amount(amount)
                .accountNumberFrom(accountId)
                .userId(userRepository.findByAccountNumber(accountId).getId())
                .createdDate(LocalDateTime.now())
                .methodName("Deposit")
                .build();
        transactionsBankRepo.save(deposit);
        user.setAmount(user.getAmount() + amount);
        return userRepository.save(user);
    }

    @Override
    public User withdraw(UUID accountId, Double amount) {
        if(checkBalance(accountId, amount)) {
            User user = userRepository.findByAccountNumber(accountId);
            TransactionsBank transactionsBank = TransactionsBank.builder()
                    .accountNumberTo(accountId)
                    .amount(amount)
                    .accountNumberFrom(accountId)
                    .userId(userRepository.findByAccountNumber(accountId).getId())
                    .createdDate(LocalDateTime.now())
                    .methodName("Withdraw")
                    .build();
            transactionsBankRepo.save(transactionsBank);
            user.setAmount(user.getAmount() - amount);
            return userRepository.save(user);
        }
        else {
            throw  new RuntimeException("Balance not enough");
        }
    }
    @Override
    public User transfer(UUID from, UUID to, Double amount) {
        if(userRepository.findByAccountNumber(to) == null || userRepository.findByAccountNumber(from) == null) {
            throw  new RuntimeException("Account not found");
        }
        if(checkBalance(from, amount)) {
            User fromUser = userRepository.findByAccountNumber(from);
            fromUser.setAmount(fromUser.getAmount() - amount);
            User toUser = userRepository.findByAccountNumber(to);
            TransactionsBank transactionsBank = TransactionsBank.builder()
                    .accountNumberTo(to)
                    .amount(amount)
                    .accountNumberFrom(from)
                    .userId(fromUser.getId())
                    .createdDate(LocalDateTime.now())
                    .methodName("Transfer")
                    .amount(amount)
                    .build();
            transactionsBankRepo.save(transactionsBank);
            toUser.setAmount(toUser.getAmount() + amount);
            return userRepository.save(fromUser);
        }
        else {
            throw  new RuntimeException("Balance not enough");
        }
    }
    private boolean checkBalance(UUID accountId, Double amount) {
        return userRepository.findByAccountNumber(accountId).getAmount() >= amount;
    }
}
