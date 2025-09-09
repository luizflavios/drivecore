package br.com.drivecore.domain.tire.exception;

import java.util.UUID;

public class TireInUseException extends RuntimeException {

    public TireInUseException(UUID id) {
        super(
                String.format("Tire position can´t be created because this tire %s is already in use", id)
        );
    }

}
