package org.example.bankapp.service;

import lombok.extern.slf4j.Slf4j;

import org.example.bankapp.model.TransactionsBank;
import org.example.bankapp.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class BankServiceImpl implements BankService {

    private final Map<UUID, User> userMap;
    private final Map<Integer, TransactionsBank> transactionsBankMap;
    private final User user;

    public BankServiceImpl() {
        userMap = new HashMap<>();
        transactionsBankMap = new HashMap<>();
        UUID userId = UUID.randomUUID();
        UUID userId1 = UUID.randomUUID();
        user = User.builder()
                .accountNumber(userId)
                .username("AmirZiya")
                .password(UUID.randomUUID().toString())
                .amount(1000)
                .build();
        User user1 = User.builder()
                .accountNumber(userId1)
                .username("Ali")
                .password(UUID.randomUUID().toString())
                .amount(1200)
                .build();


        userMap.put(userId,user);
        userMap.put(userId1,user1);

        System.out.println(userId + " created");
        System.out.println(userId1 + " created");
    }

    @Override
    public Optional<Double> viewBalance(UUID accountId) {
        TransactionsBank transactionsBank = TransactionsBank.builder()
                .createdDate(LocalDateTime.now())
                .accountNumberFrom(user.getAccountNumber())
                .accountNumberTo(user.getAccountNumber())
                .amount(user.getAmount())
                .methodName("ViewBalance")
                .build();
        transactionsBankMap.put(user.getId(), transactionsBank);
        return Optional.ofNullable(userMap.get(accountId)).map(User::getAmount);
    }

    @Override
    public User deposit(UUID accountId, Double amount) {
        if(amount > 0) {
            TransactionsBank deposit = TransactionsBank.builder()
                    .methodName("Deposit")
                    .amount(amount)
                    .accountNumberFrom(accountId)
                    .accountNumberTo(accountId)
                    .createdDate(LocalDateTime.now())
                    .build();
            transactionsBankMap.put(user.getId(), deposit);
            userMap.get(accountId).setAmount(userMap.get(accountId).getAmount() + amount);
        }
        return userMap.get(accountId);
    }

    @Override
    public User withdraw(UUID accountId, Double amount) {
        if(amount > 0) {
            TransactionsBank transactionsBank = TransactionsBank.builder()
                    .methodName("Withdraw")
                    .amount(amount)
                    .accountNumberFrom(accountId)
                    .accountNumberTo(accountId)
                    .createdDate(LocalDateTime.now())
                    .build();
            userMap.get(accountId).setAmount(userMap.get(accountId).getAmount() - amount);
        }
        return userMap.get(accountId);
    }

    @Override
    public User transfer(UUID from, UUID to, Double amount) {
        TransactionsBank transactionsBank = TransactionsBank.builder()
                .methodName("Transfer")
                .amount(amount)
                .accountNumberFrom(from)
                .accountNumberTo(to)
                .createdDate(LocalDateTime.now())
                .build();
        User userFrom = userMap.get(from);
        User userTo = userMap.get(to);
        userFrom.setAmount(userFrom.getAmount() - amount);
        userTo.setAmount(userTo.getAmount() + amount);
        return userFrom;
    }
}
