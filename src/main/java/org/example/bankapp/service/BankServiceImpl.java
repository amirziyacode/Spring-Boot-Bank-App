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
        UUID userId1 = UUID.randomUUID();
        val user = User.builder()
                .accountNumber(userId)
                .username("AmirZiya")
                .password(UUID.randomUUID().toString())
                .amount(1000)
                .build();
        val user1 = User.builder()
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
        return Optional.ofNullable(userMap.get(accountId)).map(User::getAmount);
    }

    @Override
    public User deposit(UUID accountId, Double amount) {
        if(amount > 0)
            userMap.get(accountId).setAmount(userMap.get(accountId).getAmount() + amount);
        return userMap.get(accountId);
    }

    @Override
    public User withdraw(UUID accountId, Double amount) {
        if(amount > 0)
            userMap.get(accountId).setAmount(userMap.get(accountId).getAmount() - amount);
        return userMap.get(accountId);
    }

    @Override
    public User transfer(UUID from, UUID to, Double amount) {
        User userFrom = userMap.get(from);
        User userTo = userMap.get(to);
        userFrom.setAmount(userFrom.getAmount() - amount);
        userTo.setAmount(userTo.getAmount() + amount);
        return userFrom;
    }
}
