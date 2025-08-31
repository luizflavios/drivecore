package br.com.drivecore.controller.contract.delivery.exception;

import java.util.UUID;

public class DeliveryNotFoundException extends RuntimeException {

    public DeliveryNotFoundException(UUID id) {
        super(
                String.format("Delivery not found - %s", id)
        );
    }

}
