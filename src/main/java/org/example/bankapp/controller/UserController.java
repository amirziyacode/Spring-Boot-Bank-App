package org.example.bankapp.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bankapp.model.MassageResponse;
import org.example.bankapp.model.User;
import org.example.bankapp.model.UserPassword;
import org.example.bankapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;


    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody @Valid User user) {
        User save = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @PostMapping("forgetPassword/{id}")
    public ResponseEntity<UserPassword> forgetPassword(@PathVariable Integer id,@RequestBody @Valid UserPassword user) {
        userService.forgetPassword(id, user);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<MassageResponse> login(@RequestBody @Valid User user) {
        userService.loadUser(user.getUsername(), user.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(new MassageResponse("Login Successfully !"));
    }
}
