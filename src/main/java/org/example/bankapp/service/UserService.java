package org.example.bankapp.service;


import org.example.bankapp.model.User;

public interface UserService {
    User save(User user);
    User findById(Integer id);
}
