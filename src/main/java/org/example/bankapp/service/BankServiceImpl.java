package org.example.bankapp.service;

import lombok.val;
import org.example.bankapp.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
                .amount(1000)
                .build();

        userMap.put(userId,user);

        System.out.printf(userId + " created");
    }

    @Override
    public Optional<Double> viewBalance(UUID accountId) {
        return Optional.ofNullable(userMap.get(accountId)).map(User::getAmount);
    }

    @Override
    public void deposit(UUID accountId, Double amount) {

    }

    @Override
    public User withdraw(UUID accountId, Double amount) {
        if(amount > 0)
            userMap.get(accountId).setAmount(userMap.get(accountId).getAmount() - amount);
        return userMap.get(accountId);
    }

    @Override
    public void transfer(UUID from, UUID to, Double amount) {

    }
}
