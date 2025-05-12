package com.tacticalhacker.th.backend.repository;

import com.tacticalhacker.th.backend.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {
}