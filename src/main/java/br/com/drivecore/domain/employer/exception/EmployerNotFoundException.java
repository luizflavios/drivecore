package br.com.drivecore.domain.employer.exception;

import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

public class EmployerNotFoundException extends EntityNotFoundException {

    public EmployerNotFoundException(UUID id) {
        super(String.format("Employer not found - %s", id));
    }

}
