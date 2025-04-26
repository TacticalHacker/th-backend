package com.tacticalhacker.th.backend.controller;

import com.tacticalhacker.th.backend.dto.AuthResponse;
import com.tacticalhacker.th.backend.dto.LoginRequest;
import com.tacticalhacker.th.backend.dto.RegisterRequest;
import com.tacticalhacker.th.backend.service.AuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody RegisterRequest request) {
        log.info("Registering user: {}", request);
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        log.info("Logging in user: {}", request);
        return ResponseEntity.ok(authService.login(request));
    }
}

