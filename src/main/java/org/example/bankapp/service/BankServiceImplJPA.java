package org.example.bankapp.service;

import org.example.bankapp.model.User;
import org.example.bankapp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Primary
public class BankServiceImplJPA implements BankService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<Double> viewBalance(UUID accountId) {
        return Optional.of(userRepository.findByAccountNumber(accountId).getAmount());
    }

    @Override
    public User deposit(UUID accountId, Double amount) {
        return null;
    }

    @Override
    public User withdraw(UUID accountId, Double amount) {
        return null;
    }

    @Override
    public User transfer(UUID from, UUID to, Double amount) {
        return null;
    }
}
