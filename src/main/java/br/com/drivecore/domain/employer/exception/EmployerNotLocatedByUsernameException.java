package br.com.drivecore.domain.employer.exception;

import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

public class EmployerNotLocatedByUsernameException extends EntityNotFoundException {

    public EmployerNotLocatedByUsernameException(UUID id) {
        super(
                String.format("Employer not found by current user - %s", id)
        );
    }

}
