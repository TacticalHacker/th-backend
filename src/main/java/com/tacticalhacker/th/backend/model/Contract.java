package com.tacticalhacker.th.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contract {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    private LocalDateTime sentAt;
    private String status; // SENT, ACCEPTED, REJECTED
    private LocalDateTime acceptedAt;
    private LocalDateTime rejectedAt;
    private String contractDetails;
}