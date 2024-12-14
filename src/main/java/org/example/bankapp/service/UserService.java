package org.example.bankapp.service;


import org.example.bankapp.model.TransactionsBank;
import org.example.bankapp.model.User;
import org.example.bankapp.model.UserPassword;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    Optional<List<TransactionsBank>> getTransactions(Long userId);
    User forgetPassword(Integer id, UserPassword user);
}
