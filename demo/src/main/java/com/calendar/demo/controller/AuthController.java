package com.calendar.demo.controller;

import com.calendar.demo.dto.LoginRequest;
import com.calendar.demo.dto.UserRegisterRequest;
import com.calendar.demo.dto.UserResponse;
import com.calendar.demo.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService AuthService) {
        this.authService = AuthService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRegisterRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request, HttpServletRequest req) {
        return ResponseEntity.ok(authService.login(request, req));
    }
}
