package org.example.bankapp.service;

import lombok.val;
import org.example.bankapp.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class BankServiceImpl implements BankService {

    private final Map<UUID, User> userMap;

    public BankServiceImpl() {
        userMap = new HashMap<>();
        UUID userId = UUID.randomUUID();
        val user = User.builder()
                .accountNumber(userId)
                .username("AmirZiya")
                .password(UUID.randomUUID().toString())
                .balance(1000L)
                .build();

        userMap.put(userId,user);

        System.out.printf(userId + " created");
    }

    @Override
    public Long viewBalance(UUID accountId) {
        return userMap.get(accountId).getBalance();
    }

    @Override
    public void deposit(UUID accountId, Long amount) {

    }

    @Override
    public void withdraw(UUID accountId, Long amount) {
        userMap.get(accountId).setBalance(userMap.get(accountId).getBalance() - amount);
    }

    @Override
    public void transfer(UUID from, UUID to, Long amount) {

    }
}
