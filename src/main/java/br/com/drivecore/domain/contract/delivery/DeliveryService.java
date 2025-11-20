package br.com.drivecore.domain.contract.delivery;

import br.com.drivecore.core.specification.FilterCriteriaSpecification;
import br.com.drivecore.core.specification.model.FilterCriteria;
import br.com.drivecore.domain.contract.delivery.enums.DeliveryStatus;
import br.com.drivecore.infrastructure.persistence.contract.DeliveryRepository;
import br.com.drivecore.infrastructure.persistence.contract.entities.DeliveryEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public void saveDelivery(DeliveryEntity deliveryEntity) {
        if (deliveryEntity.getReferenceNumber() == null) {
            long nextReferenceNumber = deliveryRepository.getMaxReferenceNumber() + 1;
            deliveryEntity.setReferenceNumber(nextReferenceNumber);
        }
        deliveryRepository.save(deliveryEntity);
    }

    public long countActiveDeliveries() {
        return deliveryRepository.countByFinalDateNull();
    }

    public DeliveryEntity findById(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId).orElseThrow(EntityNotFoundException::new);
    }

    public void startDelivery(DeliveryEntity deliveryEntity,
                              LocalDate startDate, Long initialMileage) {
        deliveryEntity.setStartDate(startDate);
        deliveryEntity.setDeliveryStatus(DeliveryStatus.IN_PROGRESS);
        deliveryEntity.setInitialMileage(initialMileage);

        saveDelivery(deliveryEntity);
    }

    public void closingDelivery(DeliveryEntity deliveryEntity,
                                LocalDate finalDate, Long finalMileage) {
        deliveryEntity.setFinalDate(finalDate);
        deliveryEntity.setDeliveryStatus(DeliveryStatus.CLOSING);
        deliveryEntity.setFinalMileage(finalMileage);

        saveDelivery(deliveryEntity);
    }

    public Page<DeliveryEntity> listDeliveriesPageableAndFiltered(PageRequest pageRequest, List<FilterCriteria> filters) {
        return filters != null && !filters.isEmpty() ?
                deliveryRepository.findAll(new FilterCriteriaSpecification<>(filters), pageRequest) :
                deliveryRepository.findAll(pageRequest);
    }

    public void finishDelivery(DeliveryEntity deliveryEntity) {
        deliveryEntity.setDeliveryStatus(DeliveryStatus.CLOSED);
        saveDelivery(deliveryEntity);
    }
}
