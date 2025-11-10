package br.com.drivecore.domain.contract.delivery;

import br.com.drivecore.infrastructure.persistence.contract.DeliveryRepository;
import br.com.drivecore.infrastructure.persistence.contract.entities.DeliveryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public void saveDelivery(DeliveryEntity deliveryEntity) {
        deliveryRepository.save(deliveryEntity);
    }

    public long countActiveDeliveries() {
        return deliveryRepository.countByFinalDateNull();
    }

}
