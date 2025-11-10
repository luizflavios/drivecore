package br.com.drivecore.application.contract.delivery;

import br.com.drivecore.controller.contract.delivery.model.CreateDeliveryRequestDTO;
import br.com.drivecore.controller.contract.delivery.model.DeliveryResponseDTO;
import br.com.drivecore.domain.contract.delivery.DeliveryService;
import br.com.drivecore.domain.contract.delivery.mapper.DeliveryMapper;
import br.com.drivecore.domain.machine.wheeling.TruckTrailerCombinationService;
import br.com.drivecore.domain.machine.wheeling.exception.InvalidTruckTrailerCombinationException;
import br.com.drivecore.infrastructure.persistence.contract.entities.DeliveryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.lang.Boolean.FALSE;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryApplicationService {

    private final DeliveryService deliveryService;
    private final TruckTrailerCombinationService truckTrailerCombinationService;

    public DeliveryResponseDTO createDelivery(CreateDeliveryRequestDTO createDeliveryRequestDTO) {
        checkIfTruckTrailerIsActive(createDeliveryRequestDTO.getTruckTrailerCombinationId());

        DeliveryEntity deliveryEntity = DeliveryMapper.INSTANCE.toEntity(createDeliveryRequestDTO);

        deliveryService.saveDelivery(deliveryEntity);

        log.info("Create delivery: {}", deliveryEntity);

        return DeliveryMapper.INSTANCE.toResponseDTO(deliveryEntity);
    }

    private void checkIfTruckTrailerIsActive(UUID truckTrailerCombinationId) {
        Boolean truckTrailerIsActive = truckTrailerCombinationService
                .checkIfIsActive(truckTrailerCombinationId);

        if (FALSE.equals(truckTrailerIsActive)) {
            log.error("Truck-Trailer combination {} is not active to create delivery.", truckTrailerCombinationId);

            throw new InvalidTruckTrailerCombinationException(
                    String.format("Truck-Trailer combination %s is not active.", truckTrailerCombinationId)
            );
        }
    }

}
