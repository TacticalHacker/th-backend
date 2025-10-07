package com.tacticalhacker.th.backend.service;

import com.tacticalhacker.th.backend.dto.AuthResponse;
import com.tacticalhacker.th.backend.dto.LoginRequest;
import com.tacticalhacker.th.backend.dto.RegisterRequest;
import com.tacticalhacker.th.backend.model.Role;
import com.tacticalhacker.th.backend.model.User;
import com.tacticalhacker.th.backend.repository.RoleRepository;
import com.tacticalhacker.th.backend.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            log.info("User with email {} already exists", request.getEmail());
            return new AuthResponse(null, "ERR_USER_EXISTS");
        }

        Role role = roleRepository.findByName(request.getRole().toUpperCase()).orElse(null);
        if (role == null) {
            log.info("Role {} not found", request.getRole());
            return new AuthResponse(null, "ERR_ROLE_NOT_FOUND");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setYearOfBirth(request.getYearOfBirth());
        user.setRole(role);

        userRepository.save(user);

        String token = generateToken(user);

        log.info("User {} registered successfully", user.getEmail());
        return new AuthResponse(token, null);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user == null) {
            log.info("User with email {} not found", request.getEmail());
            return new AuthResponse(null, "ERR_USER_NOT_FOUND");
        }

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (Exception e) {
            log.info("Invalid password for user {}", request.getEmail());
            return new AuthResponse(null, "ERR_INVALID_PASSWORD");
        }

        String token = generateToken(user);

        log.info("User {} logged in successfully", user.getEmail());
        return new AuthResponse(token, null);
    }

    private String generateToken(User user) {
        return jwtService.generateToken(
            new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()))
            ),
            user.getFullName()
        );
    }
}

