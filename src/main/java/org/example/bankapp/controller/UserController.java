package org.example.bankapp.controller;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bankapp.model.MassageResponse;
import org.example.bankapp.model.User;
import org.example.bankapp.model.UserPassword;
import org.example.bankapp.repo.UserRepository;
import org.example.bankapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000") // port react App !!
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;


    @PostMapping("register")
    public ResponseEntity<MassageResponse> register(@RequestBody @Valid User user) {
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MassageResponse("Registering Was Successfully !"));
    }

    @PostMapping("forgetPassword/{id}")
    public ResponseEntity<UserPassword> forgetPassword(@PathVariable Integer id,@RequestBody @Valid UserPassword user) {
        userService.forgetPassword(id, user);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<MassageResponse> login(@RequestBody @Valid User user,HttpSession session) {
       boolean isAuthentication =  userService.loadUser(user.getUsername(), user.getPassword());
       if(isAuthentication) {
           session.setAttribute("username", user.getUsername());
           return ResponseEntity.status(HttpStatus.OK).body(new MassageResponse("Login Was Successfully !"));
       }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MassageResponse("Invalid username or password!"));
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestParam String userName,HttpSession session) {
        session.getAttribute("username");
        if(userRepository.findByUsername(userName) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.findByUsername(userName));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
