package br.com.drivecore.core.generics.domain.exception;

import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

public class GenericEntityNotFoundException extends EntityNotFoundException {

    private static final String DEFAULT_MESSAGE = "Entity not found - %s";

    public GenericEntityNotFoundException(UUID id) {
        super(String.format(DEFAULT_MESSAGE, id));
    }

}
