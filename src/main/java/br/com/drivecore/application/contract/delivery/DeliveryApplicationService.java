package br.com.drivecore.application.contract.delivery;

import br.com.drivecore.application.contract.ContractApplicationService;
import br.com.drivecore.controller.contract.delivery.model.CreateDeliveryRequestDTO;
import br.com.drivecore.controller.contract.delivery.model.DeliveryResponseDTO;
import br.com.drivecore.domain.contract.delivery.DeliveryService;
import br.com.drivecore.domain.contract.delivery.mapper.DeliveryMapper;
import br.com.drivecore.infrastructure.persistence.contract.entities.ContractEntity;
import br.com.drivecore.infrastructure.persistence.contract.entities.DeliveryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryApplicationService {

    private final ContractApplicationService contractApplicationService;

    private final DeliveryService deliveryService;

    @Transactional
    public DeliveryResponseDTO createDelivery(CreateDeliveryRequestDTO createDeliveryRequestDTO) {
        ContractEntity contractEntity = contractApplicationService.createDomainContract(createDeliveryRequestDTO.getContract());

        DeliveryEntity deliveryEntity = DeliveryMapper.INSTANCE.toEntity(createDeliveryRequestDTO, contractEntity);

        deliveryService.createDelivery(deliveryEntity);

        log.info("Delivery {} successfully created by - {}", deliveryEntity.getId(), contractEntity.getCreatedBy().getId());

        return DeliveryMapper.INSTANCE.toContractResponseDTO(deliveryEntity);
    }

    public DeliveryResponseDTO getDeliveryDetail(UUID deliveryId) {
        DeliveryEntity deliveryEntity = deliveryService.findById(deliveryId);

        return DeliveryMapper.INSTANCE.toContractResponseDTO(deliveryEntity);
    }

}
