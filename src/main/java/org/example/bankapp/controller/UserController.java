package org.example.bankapp.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.bankapp.model.User;
import org.example.bankapp.model.UserPassword;
import org.example.bankapp.repo.UserRepository;
import org.example.bankapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User save = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @PostMapping("/forgetPassword/{id}")
    public ResponseEntity<User> forgetPassword(@RequestBody UserPassword user, @PathVariable Integer id) {
        Optional<User> userId = userRepository.findById(id);
        if(userId.get().getPassword().equals(user.getOldPassword()) && user.getNewPassword().equals( user.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userId.get());
        }
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(userId.get());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id){
        Optional<User> byId = userService.findById(id);
        if(byId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(byId.orElse(null));
    }
}
