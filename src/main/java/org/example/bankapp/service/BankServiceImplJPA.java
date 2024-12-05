package org.example.bankapp.service;

import org.example.bankapp.model.TransactionsBank;
import org.example.bankapp.model.User;
import org.example.bankapp.repo.TransactionsBankRepo;
import org.example.bankapp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
public class BankServiceImplJPA implements BankService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionsBankRepo transactionsBankRepo;

    @Override
    public Optional<Double> viewBalance(UUID accountId) {
        double amount = userRepository.findByAccountNumber(accountId).getAmount();
        TransactionsBank balance = TransactionsBank.builder()
                .accountNumberTo(accountId)
                .amount(amount)
                .accountNumberFrom(accountId)
                .user(userRepository.findByAccountNumber(accountId))
                .methodName("ViewBalance")
                .createdDate(LocalDateTime.now())
                .build();
        transactionsBankRepo.save(balance);
        return Optional.of(amount);
    }

    @Override
    public User deposit(UUID accountId, Double amount) {
        User user = userRepository.findByAccountNumber(accountId);
        user.setAmount(user.getAmount() + amount);
        return userRepository.save(user);
    }

    @Override
    public User withdraw(UUID accountId, Double amount) {
        User user = userRepository.findByAccountNumber(accountId);
        user.setAmount(user.getAmount() - amount);
        return userRepository.save(user);
    }

    @Override
    public User transfer(UUID from, UUID to, Double amount) {
        User fromUser = userRepository.findByAccountNumber(from);
        fromUser.setAmount(fromUser.getAmount() - amount);
        User toUser = userRepository.findByAccountNumber(to);
        toUser.setAmount(toUser.getAmount() + amount);
        return fromUser;
    }
}
