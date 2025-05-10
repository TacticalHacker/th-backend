package com.tacticalhacker.th.backend.dto;

import com.tacticalhacker.th.backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String fullName;
    private String email;
    private Integer yearOfBirth;
    private Role role;
}