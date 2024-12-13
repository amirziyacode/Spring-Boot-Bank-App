package org.example.bankapp.service;


import org.example.bankapp.model.User;
import org.example.bankapp.model.UserPassword;

import java.util.Optional;

public interface UserService {
    User save(User user);
    Optional<User> findById(Integer id);
    User forgetPassword(Integer id, UserPassword user);
}
