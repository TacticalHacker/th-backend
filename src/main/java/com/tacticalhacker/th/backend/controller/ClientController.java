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

@RestController
@PreAuthorize("hasRole('CLIENT')")
@RequestMapping("/api/client")
public class ClientController {

    private final ContractRepository contractRepository;
    private final UserRepository userRepository;

    @Autowired
    public ClientController(ContractRepository contractRepository, UserRepository userRepository) {
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;
    }

    @PutMapping("/acceptContract/{contractId}")
    public ResponseEntity<String> acceptContract(@PathVariable String contractId) {
        return updateContractStatus(contractId, "ACCEPTED", true);
    }

    @PutMapping("/rejectContract/{contractId}")
    public ResponseEntity<String> rejectContract(@PathVariable String contractId) {
        return updateContractStatus(contractId, "REJECTED", false);
    }

    @GetMapping("/getContract")
    public ResponseEntity<ClientContractResponseDTO> getContract(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ClientContractResponseDTO(null, null));
        }

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

        return ResponseEntity.ok(new ClientContractResponseDTO(user, contract));
    }

    // Private helper method to reduce code duplication
    private ResponseEntity<String> updateContractStatus(String contractId, String status, boolean isAccepted) {
        try {
            Contract contract = contractRepository.findById(UUID.fromString(contractId)).orElse(null);
            if (contract == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Contract not found");
            }
            contract.setStatus(status);
            if (isAccepted) {
                contract.setAcceptedAt(LocalDateTime.now());
            } else {
                contract.setRejectedAt(LocalDateTime.now());
            }
            contractRepository.save(contract);
            return ResponseEntity.ok("Contract " + status.toLowerCase());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}