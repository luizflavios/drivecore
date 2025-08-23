package br.com.drivecore.controller.contract.delivery.exception;

import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

public class DeliveryNotFoundException extends EntityNotFoundException {

    public DeliveryNotFoundException(UUID id) {
        super(
                String.format("Delivery not found - %s", id)
        );
    }

}
