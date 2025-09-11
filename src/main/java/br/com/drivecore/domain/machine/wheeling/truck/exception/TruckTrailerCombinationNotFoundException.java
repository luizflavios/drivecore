package br.com.drivecore.domain.machine.wheeling.truck.exception;

import java.util.UUID;

public class TruckTrailerCombinationNotFoundException extends RuntimeException {

    public TruckTrailerCombinationNotFoundException(UUID id) {
        super(
                String.format("Truck trailer combination not found - %s", id)
        );
    }

}
