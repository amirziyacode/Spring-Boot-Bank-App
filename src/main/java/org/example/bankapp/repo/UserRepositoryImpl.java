package org.example.bankapp.repo;

import lombok.RequiredArgsConstructor;
import org.example.bankapp.model.User;
import org.example.bankapp.service.BankService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Primary
@Service
@RequiredArgsConstructor
public class UserRepositoryImpl implements BankService {
    private final UserRepository userRepository;
    @Override
    public Optional<Double> viewBalance(UUID accountId) {
//        return Optional.of(userRepository.findByAccountNumber(accountId));
        return null;
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
