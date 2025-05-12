package com.tacticalhacker.th.backend.controller;

import com.tacticalhacker.th.backend.dto.UserDTO;
import com.tacticalhacker.th.backend.dto.ChangeUserRoleRequest;
import com.tacticalhacker.th.backend.dto.SendContractRequest;
import com.tacticalhacker.th.backend.dto.UserWithContractStatusDTO;
import com.tacticalhacker.th.backend.model.User;
import com.tacticalhacker.th.backend.model.Role;
import com.tacticalhacker.th.backend.model.Contract;
import com.tacticalhacker.th.backend.repository.UserRepository;
import com.tacticalhacker.th.backend.repository.RoleRepository;
import com.tacticalhacker.th.backend.repository.ContractRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.tacticalhacker.th.backend.repository.ContractRepository;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
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

    @Autowired
    private ContractRepository contractRepository;

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

    @PostMapping("/sendContract")
    public ResponseEntity<?> sendContract(@RequestBody SendContractRequest request, Principal principal) {
        try {
            User admin = userRepository.findByEmail(principal.getName()).orElse(null);
            log.info("Admin : {}",admin);
            User client = userRepository.findByEmail(request.getClientId()).orElse(null);
            log.info("Client : {}",client);
            if (admin == null || client == null) {
                log.info("BAD REQUEST : Invalid admin or client");
                return ResponseEntity.badRequest().body("Invalid admin or client");
            }
            Contract contract = new Contract();
            contract.setAdmin(admin);
            contract.setClient(client);
            contract.setSentAt(LocalDateTime.now());
            contract.setStatus("SENT");
            contract.setContractDetails(request.getContractDetails());
            log.info("Contract : {}", contract);
            contractRepository.save(contract);
            return ResponseEntity.ok("Contract sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/getAllUsersWithContracts")
    public ResponseEntity<List<UserWithContractStatusDTO>> getAllUsersWithContracts() {
        List<User> users = userRepository.findAll();
        List<UserWithContractStatusDTO> userDTOs = users.stream().map(user -> {
            // Find the latest contract for this user as client (if any)
            Contract latestContract = contractRepository.findAll().stream()
                .filter(contract -> contract.getClient() != null && contract.getClient().getId().equals(user.getId()))
                .sorted((c1, c2) -> {
                    if (c1.getSentAt() == null && c2.getSentAt() == null) return 0;
                    if (c1.getSentAt() == null) return 1;
                    if (c2.getSentAt() == null) return -1;
                    return c2.getSentAt().compareTo(c1.getSentAt());
                })
                .findFirst()
                .orElse(null);

            String contractStatus;
            if (latestContract == null) {
                contractStatus = "No Contract";
            } else {
                switch (latestContract.getStatus()) {
                    case "SENT":
                        contractStatus = "Contract Sent";
                        break;
                    case "ACCEPTED":
                        contractStatus = "Contract Accepted";
                        break;
                    case "REJECTED":
                        contractStatus = "Contract Rejected";
                        break;
                    case "REVOKED":
                        contractStatus = "Contract Revoked";
                        break;
                    default:
                        contractStatus = "Unknown";
                }
            }

            return new UserWithContractStatusDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getYearOfBirth(),
                user.getRole(),
                contractStatus
            );
        }).collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }
}