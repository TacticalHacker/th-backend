package com.tacticalhacker.th.backend.controller;

import com.tacticalhacker.th.backend.dto.UserDTO;
import com.tacticalhacker.th.backend.dto.ChangeUserRoleRequest;
import com.tacticalhacker.th.backend.model.User;
import com.tacticalhacker.th.backend.model.Role;
import com.tacticalhacker.th.backend.repository.UserRepository;
import com.tacticalhacker.th.backend.repository.RoleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream()
            .map(user -> new UserDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getYearOfBirth(),
                user.getRole()
            ))
            .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @PutMapping("/changeUserRole")
    public ResponseEntity<?> changeUserRole(@RequestBody ChangeUserRoleRequest request) {
        try {
            // Find user by ID
            User user = userRepository.findById(UUID.fromString(request.getUserId()))
                    .orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User not found");
            }

            // Find role by name
            Role newRole = roleRepository.findByName(request.getNewRole()).orElse(null);
            if (newRole == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Role not found");
            }

            // Update and save
            user.setRole(newRole);
            userRepository.save(user);

            return ResponseEntity.ok("User role updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating user role: " + e.getMessage());
        }
    }
}