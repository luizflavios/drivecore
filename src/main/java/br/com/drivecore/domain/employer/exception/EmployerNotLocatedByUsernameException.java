package br.com.drivecore.domain.employer.exception;

import java.util.UUID;

public class EmployerNotLocatedByUsernameException extends RuntimeException {

    public EmployerNotLocatedByUsernameException(UUID id) {
        super(
                String.format("Employer not found by current user - %s", id)
        );
    }

}
