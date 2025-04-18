package com.tacticalhacker.th_scribes_backend.service;

import com.tacticalhacker.th_scribes_backend.dto.AuthResponse;
import com.tacticalhacker.th_scribes_backend.dto.LoginRequest;
import com.tacticalhacker.th_scribes_backend.dto.RegisterRequest;
import com.tacticalhacker.th_scribes_backend.model.Role;
import com.tacticalhacker.th_scribes_backend.model.User;
import com.tacticalhacker.th_scribes_backend.repository.RoleRepository;
import com.tacticalhacker.th_scribes_backend.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            log.info("User with email {} already exists", request.getEmail());
            throw new RuntimeException("User already exists");
        }

        Role role = roleRepository.findByName(request.getRole().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setYearOfBirth(request.getYearOfBirth());
        user.setRole(role);

        userRepository.save(user);

        String token = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(), user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + role.getName()))
                )
        );

        log.info("User {} registered successfully", user.getEmail());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(), user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()))
                )
        );
        log.info("User {} logged in successfully", user.getEmail());
        return new AuthResponse(token);
    }
}

