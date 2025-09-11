package br.com.drivecore.application.contract.delivery;

import br.com.drivecore.application.employer.EmployerApplicationService;
import br.com.drivecore.application.machine.wheeling.truck.TruckApplicationService;
import br.com.drivecore.controller.contract.delivery.model.CreateDeliveryRequestDTO;
import br.com.drivecore.controller.contract.delivery.model.DeliveryResponseDTO;
import br.com.drivecore.domain.contract.delivery.DeliveryService;
import br.com.drivecore.domain.contract.delivery.mapper.DeliveryMapper;
import br.com.drivecore.infrastructure.persistence.contract.entities.DeliveryEntity;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryApplicationService {

    private final EmployerApplicationService employerApplicationService;
    private final TruckApplicationService truckApplicationService;

    private final DeliveryService deliveryService;

    public DeliveryResponseDTO createDelivery(CreateDeliveryRequestDTO createDeliveryRequestDTO) {
        EmployerEntity employerEntity = employerApplicationService.getLoggedEmployer();

        DeliveryEntity deliveryEntity = DeliveryMapper.INSTANCE.toEntity(createDeliveryRequestDTO, employerEntity);

        if (nonNull(createDeliveryRequestDTO.getTruckId())) {
            TruckEntity truckEntity = truckApplicationService
                    .findDomainTruckById(createDeliveryRequestDTO.getTruckId());

            deliveryEntity.setTruck(truckEntity);
        }

        deliveryService.createDelivery(deliveryEntity);

        log.info("Delivery {} successfully created by - {}", deliveryEntity.getId(), deliveryEntity.getCreatedBy().getId());

        return DeliveryMapper.INSTANCE.toContractResponseDTO(deliveryEntity);
    }

    public DeliveryResponseDTO getDeliveryDetail(UUID deliveryId) {
        DeliveryEntity deliveryEntity = deliveryService.findById(deliveryId);

        return DeliveryMapper.INSTANCE.toContractResponseDTO(deliveryEntity);
    }

}
