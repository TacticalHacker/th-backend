package com.tacticalhacker.th.backend.dto;

import lombok.Data;

@Data
public class ChangeUserRoleRequest {
    private String userId;
    private String newRole;
}