package org.example.bankapp.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bankapp.model.ResponseMassage;
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
@CrossOrigin(origins = "http://localhost:5173") // port react App !!
public class UserController {

    private final UserService userService;


    @PostMapping("register")
    public ResponseEntity<ResponseMassage> register(@RequestBody @Valid User user) {
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMassage("Registering Was Successfully !"));
    }

    @PostMapping("forgetPassword/{username}")
    public ResponseEntity<UserPassword> forgetPassword(@PathVariable String username,@RequestBody @Valid UserPassword user) {
        userService.forgetPassword(username, user);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }

    @PostMapping("login")
    public ResponseEntity<ResponseMassage> login(@RequestBody @Valid User user) {
       boolean isAuthentication =  userService.loadUser(user.getUsername(), user.getPassword());
       if(isAuthentication) {
           return ResponseEntity.status(HttpStatus.OK).body(new ResponseMassage("Login Was Successfully !"));
       }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMassage("Invalid username or password!"));
    }

    @GetMapping("user")
    public ResponseEntity<Object> getUser(@RequestParam String userName) {
        User getUser = userService.getUser(userName);
        if(getUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(getUser);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found !!");
    }
}
