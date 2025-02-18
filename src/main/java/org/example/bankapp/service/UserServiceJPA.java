package org.example.bankapp.service;

import lombok.RequiredArgsConstructor;
import org.example.bankapp.model.UserPassword;
import org.example.bankapp.repo.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.bankapp.model.User;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Primary
@Service
@RequiredArgsConstructor
public class UserServiceJPA implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @Override
    public User save(User user) {
        List<User> users = userRepository.findAll();
        for (User u : users) {
            if(u.getUsername().equals(user.getUsername())) {
                throw  new IllegalArgumentException("Username already exists");
            }
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setAccountNumber(UUID.randomUUID());
        return userRepository.save(user);
    }

    @Override
    public void forgetPassword(String username, UserPassword user) {
        User getuser = userRepository.findByUsername(username);
        boolean checkPassword = bCryptPasswordEncoder.matches(user.getOldPassword(),getuser.getPassword());
        if(!checkPassword) {
            throw  new IllegalArgumentException("Password does not match !!");
        }
        if(!Objects.equals(user.getConfirmPassword(), user.getNewPassword())) {
            throw  new IllegalArgumentException("You Should Confirm Password !!");
        }

        getuser.setPassword(bCryptPasswordEncoder.encode(user.getNewPassword()));
        userRepository.save(getuser);
    }

    @Override
    public Boolean loadUser(String username, String password) {
        User getUser = userRepository.findByUsername(username);
        return bCryptPasswordEncoder.matches(password, getUser.getPassword());
    }

    @Override
    public User getUser(String username) {
        if(userRepository.findByUsername(username) != null) {
            return userRepository.findByUsername(username);
        }
        return null;
    }
}
