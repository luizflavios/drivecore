package br.com.drivecore.domain.employer.exception;

import java.util.UUID;

public class EmployerNotFoundException extends RuntimeException {

    public EmployerNotFoundException(UUID id) {
        super(
                String.format("Employer not found  - %s", id)
        );
    }

}
