package br.com.drivecore.domain.contract.delivery;

import br.com.drivecore.controller.contract.delivery.exception.DeliveryNotFoundException;
import br.com.drivecore.infrastructure.persistence.contract.DeliveryRepository;
import br.com.drivecore.infrastructure.persistence.contract.entities.DeliveryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public void createDelivery(DeliveryEntity deliveryEntity) {
        deliveryRepository.save(deliveryEntity);
    }

    public DeliveryEntity findById(UUID deliveryId) {
        return deliveryRepository
                .findById(deliveryId)
                .orElseThrow(() -> new DeliveryNotFoundException(deliveryId));
    }
}
