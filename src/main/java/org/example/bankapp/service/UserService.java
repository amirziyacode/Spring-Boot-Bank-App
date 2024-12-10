package org.example.bankapp.service;


import org.example.bankapp.model.User;

import java.util.Optional;

public interface UserService {
    User save(User user);
    Optional<User> findById(Integer id);
    User updateUser(Integer idUser, User newUser);
}
