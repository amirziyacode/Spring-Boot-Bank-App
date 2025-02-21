package org.example.bankapp.service;


import org.example.bankapp.model.User;
import org.example.bankapp.model.UserPassword;

public interface UserService {
    User save(User user);
    void forgetPassword(String username, UserPassword user);
    Boolean loadUser(String username, String password);
    User getUser(String username);
}
