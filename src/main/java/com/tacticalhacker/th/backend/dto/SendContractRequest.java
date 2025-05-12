package com.tacticalhacker.th.backend.dto;

import lombok.Data;

@Data
public class SendContractRequest {
    private String clientId;
    private String contractDetails;
}