package br.com.drivecore.domain.contract.exception;

import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

public class ContractNotFoundException extends EntityNotFoundException {

    public ContractNotFoundException(UUID id) {
        super(String.format("Contract not found - %s", id));
    }

}
