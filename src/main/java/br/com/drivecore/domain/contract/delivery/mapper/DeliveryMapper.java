package br.com.drivecore.domain.contract.delivery.mapper;

import br.com.drivecore.controller.contract.delivery.model.CreateDeliveryRequestDTO;
import br.com.drivecore.controller.contract.delivery.model.DeliveryResponseDTO;
import br.com.drivecore.domain.contract.enums.ContractType;
import br.com.drivecore.infrastructure.persistence.contract.entities.DeliveryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {ContractType.class})
public interface DeliveryMapper {

    DeliveryMapper INSTANCE = Mappers.getMapper(DeliveryMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "type", expression = "java(ContractType.DELIVERY)")
    DeliveryEntity toEntity(CreateDeliveryRequestDTO createDeliveryRequestDTO);

    DeliveryResponseDTO toResponseDTO(DeliveryEntity deliveryEntity);

}
