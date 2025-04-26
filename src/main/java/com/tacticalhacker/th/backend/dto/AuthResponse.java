package com.tacticalhacker.th.backend.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String error;

    public AuthResponse(String token, String error) {
        this.token = token;
        this.error = error;
    }
}

