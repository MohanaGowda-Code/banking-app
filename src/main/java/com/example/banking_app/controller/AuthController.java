package com.example.banking_app.controller;

import com.example.banking_app.entity.User;
import com.example.banking_app.exception.InvalidCredentialsException;
import com.example.banking_app.repository.UserRepository;
import com.example.banking_app.service.UserService;
import com.example.banking_app.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil, UserService UserService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userService = UserService;
    }

    // -------------------- Register --------------------
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        if (userRepository.existsById(request.get("username"))) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = new User();
        user.setUsername(request.get("username"));
        user.setPassword(passwordEncoder.encode(request.get("password")));
        user.setRoles("USER"); // default role
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message","User registered successfully"));
    }

    // -------------------- Login --------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        User user = userRepository.findById(request.get("username"))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.get("password"), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials. Please enter correct username or password.");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        user.setToken(token);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserDetailsByName(@PathVariable String username){
        User  user = userService.getUserDetailsByName(username);
        return ResponseEntity.ok(user);
    }
}
