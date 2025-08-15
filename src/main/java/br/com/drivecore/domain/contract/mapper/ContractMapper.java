package br.com.drivecore.domain.contract.mapper;

import br.com.drivecore.domain.contract.model.ContractRequestDTO;
import br.com.drivecore.domain.contract.model.ContractResponseDTO;
import br.com.drivecore.domain.contract.model.CreateContractRequestDTO;
import br.com.drivecore.infrastructure.persistence.contract.entities.ContractEntity;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.user.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper(imports = {BigDecimal.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContractMapper {

    ContractMapper INSTANCE = Mappers.getMapper(ContractMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "commission", expression = "java(requestDTO.getCommission() != null ? requestDTO.getCommission() : BigDecimal.ZERO)")
    @Mapping(target = "responsible", source = "responsible")
    ContractEntity toEntity(CreateContractRequestDTO requestDTO, EmployerEntity responsible, UserEntity createdBy);

    ContractResponseDTO toContractResponseDTO(ContractEntity contractEntity);

    @Mapping(target = "destiny", source = "contractRequestDTO.destiny")
    @Mapping(target = "startDate", source = "contractRequestDTO.startDate")
    @Mapping(target = "finalDate", source = "contractRequestDTO.finalDate")
    @Mapping(target = "initialKilometer", source = "contractRequestDTO.initialKilometer")
    @Mapping(target = "finalKilometer", source = "contractRequestDTO.finalKilometer")
    @Mapping(target = "commission", source = "contractRequestDTO.commission")
    @Mapping(target = "contractValue", source = "contractRequestDTO.contractValue")
    void copyProperties(ContractRequestDTO contractRequestDTO, @MappingTarget ContractEntity contractEntity);


}
