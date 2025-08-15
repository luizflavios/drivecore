package br.com.drivecore.domain.user.exception;

import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(UUID id) {
        super(String.format("User not found - %s", id));
    }

}
