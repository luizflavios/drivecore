package br.com.drivecore.domain.machine.wheeling.trailer.exception;

import java.util.UUID;

public class TrailerNotFoundException extends RuntimeException {

    public TrailerNotFoundException(UUID id) {
        super(
                String.format("Trailer not found - %s", id)
        );
    }

}
