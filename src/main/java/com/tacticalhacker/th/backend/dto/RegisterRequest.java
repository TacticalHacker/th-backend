package com.tacticalhacker.th.backend.dto;

import lombok.Data;
import lombok.ToString;

@Data
public class RegisterRequest {
    private String fullName;
    private String email;
    @ToString.Exclude
    private String password;
    private int yearOfBirth;
    private String role;
}
