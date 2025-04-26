package com.tacticalhacker.th.backend.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private int yearOfBirth;
    private String role;
}
