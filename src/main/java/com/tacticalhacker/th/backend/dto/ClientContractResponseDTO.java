package com.tacticalhacker.th.backend.dto;

import com.tacticalhacker.th.backend.model.User;
import com.tacticalhacker.th.backend.model.Contract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientContractResponseDTO {
    private User user;
    private Contract contract; // can be null if no contract exists
}