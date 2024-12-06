package org.example.bankapp.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bankapp.model.User;
import org.example.bankapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User save = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserOne(@PathVariable Integer id){
        User user = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
