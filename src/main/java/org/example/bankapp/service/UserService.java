package org.example.bankapp.service;


import org.example.bankapp.model.User;
import org.example.bankapp.model.UserPassword;

public interface UserService {
    User save(User user);
    User forgetPassword(Integer id, UserPassword user);
}
