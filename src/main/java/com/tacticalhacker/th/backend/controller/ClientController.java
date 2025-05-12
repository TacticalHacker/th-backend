package com.tacticalhacker.th.backend.controller;

import com.tacticalhacker.th.backend.dto.ClientContractResponseDTO;
import com.tacticalhacker.th.backend.model.Contract;
import com.tacticalhacker.th.backend.repository.ContractRepository;
import com.tacticalhacker.th.backend.model.User;
import com.tacticalhacker.th.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@PreAuthorize("hasRole('CLIENT')")
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private UserRepository userRepository;

    @PutMapping("/acceptContract/{contractId}")
    public ResponseEntity<?> acceptContract(@PathVariable String contractId) {
        try {
            Contract contract = contractRepository.findById(UUID.fromString(contractId)).orElse(null);
            if (contract == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Contract not found");
            }
            contract.setStatus("ACCEPTED");
            contract.setAcceptedAt(LocalDateTime.now());
            contractRepository.save(contract);
            return ResponseEntity.ok("Contract accepted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/rejectContract/{contractId}")
    public ResponseEntity<?> rejectContract(@PathVariable String contractId) {
        try {
            Contract contract = contractRepository.findById(UUID.fromString(contractId)).orElse(null);
            if (contract == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Contract not found");
            }
            contract.setStatus("REJECTED");
            contract.setRejectedAt(LocalDateTime.now());
            contractRepository.save(contract);
            return ResponseEntity.ok("Contract rejected");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/getContract")
    public ResponseEntity<ClientContractResponseDTO> getContract(Principal principal) {
        try {
            User user = userRepository.findByEmail(principal.getName()).orElse(null);
            if (user == null) {
                // Return a DTO with nulls to match the method signature
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ClientContractResponseDTO(null, null));
            }
            // Find the latest contract for this client (if any)
            Contract contract = contractRepository.findAll().stream()
                .filter(c -> c.getClient() != null && c.getClient().getId().equals(user.getId()))
                .sorted((c1, c2) -> {
                    if (c1.getSentAt() == null && c2.getSentAt() == null) return 0;
                    if (c1.getSentAt() == null) return 1;
                    if (c2.getSentAt() == null) return -1;
                    return c2.getSentAt().compareTo(c1.getSentAt());
                })
                .findFirst()
                .orElse(null);

            ClientContractResponseDTO response = new ClientContractResponseDTO(user, contract);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Return a DTO with nulls to match the method signature
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ClientContractResponseDTO(null, null));
        }
    }
}