package br.com.drivecore.domain.contract.delivery.mapper;

import br.com.drivecore.controller.contract.delivery.model.CreateDeliveryRequestDTO;
import br.com.drivecore.controller.contract.delivery.model.DeliveryResponseDTO;
import br.com.drivecore.controller.contract.delivery.model.SummaryDeliveryResponseDTO;
import br.com.drivecore.domain.contract.delivery.enums.DeliveryStatus;
import br.com.drivecore.domain.contract.enums.ContractType;
import br.com.drivecore.infrastructure.persistence.contract.entities.DeliveryEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckTrailerCombinationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {ContractType.class, DeliveryStatus.class})
public interface DeliveryMapper {

    DeliveryMapper INSTANCE = Mappers.getMapper(DeliveryMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "referenceNumber", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "type", expression = "java(ContractType.DELIVERY)")
    @Mapping(target = "deliveryStatus", expression = "java(DeliveryStatus.OPEN)")
    @Mapping(target = "commission", ignore = true)
    @Mapping(target = "truckTrailerCombination", source = "truckTrailerCombination")
    @Mapping(target = "expenses", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "startDate", ignore = true)
    @Mapping(target = "finalDate", ignore = true)
    @Mapping(target = "initialMileage", ignore = true)
    @Mapping(target = "finalMileage", ignore = true)
    DeliveryEntity toEntity(CreateDeliveryRequestDTO createDeliveryRequestDTO
            , TruckTrailerCombinationEntity truckTrailerCombination);

    @Mapping(target = "truckTrailerCombination.couplingLicensePlate", source =
            "deliveryEntity", qualifiedByName = "buildCouplingLicensePlate")
    DeliveryResponseDTO toResponseDTO(DeliveryEntity deliveryEntity);

    @Named("buildCouplingLicensePlate")
    default String buildCouplingLicensePlate(DeliveryEntity deliveryEntity) {
        return String.format("%s - %s",
                deliveryEntity.getTruckTrailerCombination().getTruck().getLicensePlate(),
                deliveryEntity.getTruckTrailerCombination().getTrailer().getLicensePlate()
        );
    }

    @Mapping(target = "truckTrailerCombination.couplingLicensePlate", source =
            "deliveryEntity", qualifiedByName = "buildCouplingLicensePlate")
    SummaryDeliveryResponseDTO toSummaryResponseDTO(DeliveryEntity deliveryEntity);
}
