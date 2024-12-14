package org.example.bankapp.service;

import lombok.RequiredArgsConstructor;
import org.example.bankapp.model.TransactionsBank;
import org.example.bankapp.model.UserPassword;
import org.example.bankapp.repo.TransactionsBankRepo;
import org.example.bankapp.repo.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.bankapp.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Service
@RequiredArgsConstructor
public class UserServiceJPA implements UserService {

    private final UserRepository userRepository;
    private  final TransactionsBankRepo transactionsBank;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @Override
    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setAccountNumber(UUID.randomUUID());
        return userRepository.save(user);
    }

    @Override
    public Optional<List<TransactionsBank>> getTransactions(Integer userId) {
        return Optional.ofNullable(transactionsBank.findByUserId(userId));
    }


    @Override
    public User forgetPassword(Integer id, UserPassword user) {
        Optional<User> byId = userRepository.findById(id);
        byId.get().setPassword(bCryptPasswordEncoder.encode(user.getNewPassword()));
        return userRepository.save(byId.get());
    }


}
