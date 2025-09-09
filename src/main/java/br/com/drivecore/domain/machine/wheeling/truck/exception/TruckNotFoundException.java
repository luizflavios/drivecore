package br.com.drivecore.domain.machine.wheeling.truck.exception;

import java.util.UUID;

public class TruckNotFoundException extends RuntimeException {

    public TruckNotFoundException(UUID id) {
        super(
                String.format("Truck not found - %s", id)
        );
    }

}
